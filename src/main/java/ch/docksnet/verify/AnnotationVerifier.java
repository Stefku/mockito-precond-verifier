package ch.docksnet.verify;

import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public interface AnnotationVerifier {
    static String errorPrefix = "Constraint Validation Error: ";

    boolean canHandle(Annotation annotation);

    void assertViolation(Annotation annotation, Object value);
}
