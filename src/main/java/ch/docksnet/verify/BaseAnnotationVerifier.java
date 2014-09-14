package ch.docksnet.verify;

import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public abstract class BaseAnnotationVerifier implements AnnotationVerifier {

    protected final Class<?> baseAnnotation;

    protected BaseAnnotationVerifier(Class<?> baseAnnotation) {
        this.baseAnnotation = baseAnnotation;
    }

    @Override
    public boolean canHandle(Annotation annotation) {
        if (baseAnnotation.isInstance(annotation)) {
            return true;
        }
        return false;
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        if (!canHandle(annotation)) {
            throw new IllegalArgumentException("Cannot handle " + annotation);
        }
    }

}
