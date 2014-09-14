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
        verifiers = new ArrayList<>();
        verifiers.add(new NotNullAnnotationVerifier());
        verifiers.add(new MaxAnnotationVerifier());
    }

    public void verifyConstraints(Object mock) {
        if (mock == null) {
            throw new NotAMockException("Argument should be a mock, but is null");
        }
        if (!mockUtil.isMock(mock)) {
            throw new NotAMockException("Argument should be a mock, but is: " + mock.getClass().getSimpleName());
        }
        List<Invocation> invokations = getInvokations(mock);
        for (Invocation invokation : invokations) {
            Object[] rawArguments = invokation.getRawArguments();
            Annotation[][] parameterAnnotations = invokation.getMethod().getParameterAnnotations();
            verifyValidationConstraints(parameterAnnotations, rawArguments);
        }
    }

    private java.util.List<Invocation> getInvokations(Object mock) {
        MockUtil mockUtil = new MockUtil();
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
