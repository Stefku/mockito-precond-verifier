package ch.docksnet.verify;

import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.internal.InternalMockHandler;
import org.mockito.internal.stubbing.InvocationContainer;
import org.mockito.internal.util.MockUtil;
import org.mockito.invocation.Invocation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stefan Zeller
 */
public class ValidationConstraintVerifier {

    private List<AnnotationVerifier> verifiers;
    private MockUtil mockUtil = new MockUtil();

    public ValidationConstraintVerifier() {
        populateVerifiers();
    }

    private void populateVerifiers() {
        verifiers = new ArrayList<>();
        verifiers.add(new NotNullAnnotationVerifier());
        verifiers.add(new MaxAnnotationVerifier());
    }

    public void verifyConstraints(Object mock) {
        assertNotNull(mock);
        assertIsMock(mock);

        List<Invocation> invocations = getInvocations(mock);
        for (Invocation invocation : invocations) {
            verifyInvocation(invocation);
        }
    }

    private void assertNotNull(Object mock) {
        if (mock == null) {
            throw new NotAMockException("Argument should be a mock, but is null");
        }
    }

    private void assertIsMock(Object mock) {
        if (!mockUtil.isMock(mock)) {
            throw new NotAMockException("Argument should be a mock, but is: " + mock.getClass().getSimpleName());
        }
    }

    private void verifyInvocation(Invocation invocation) {
        Object[] rawArguments = invocation.getRawArguments();
        Annotation[][] parameterAnnotations = invocation.getMethod().getParameterAnnotations();
        verifyValidationConstraints(parameterAnnotations, rawArguments);
    }

    private java.util.List<Invocation> getInvocations(Object mock) {
        InternalMockHandler<?> mockHandler = mockUtil.getMockHandler(mock);
        InvocationContainer invocationContainer = mockHandler.getInvocationContainer();
        return invocationContainer.getInvocations();
    }

    private void verifyValidationConstraints(Annotation[][] parameterAnnotations, Object[] rawArguments) {
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            Object rawArgument = rawArguments[i];
            verifyArgumentWithAnnotations(parameterAnnotation, rawArgument);
        }
    }

    private void verifyArgumentWithAnnotations(Annotation[] parameterAnnotation, Object rawArgument) {
        for (Annotation annotation : parameterAnnotation) {
            for (AnnotationVerifier verifier : verifiers) {
                if (verifier.canHandle(annotation)) {
                    verifier.assertViolation(annotation, rawArgument);
                }
            }
        }
    }

}
