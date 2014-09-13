package ch.docksnet.verify;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public class NotNullAnnotationVerifier implements AnnotationVerifier {

    @Override
    public boolean canHandle(Annotation annotation) {
        if (annotation instanceof NotNull) {
            return true;
        }
        return false;
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        if (!canHandle(annotation)) {
            throw new IllegalArgumentException("Cannot handle " + annotation);
        }
        if (value == null) {
            throw new AssertionError(errorPrefix + "Called @NotNull parameter with null");
        }
    }
}
