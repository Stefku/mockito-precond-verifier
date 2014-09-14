package ch.docksnet.verify;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public class NotNullAnnotationVerifier extends BaseAnnotationVerifier {

    protected NotNullAnnotationVerifier() {
        super(NotNull.class);
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        super.assertViolation(annotation, value);

        if (value == null) {
            throw new AssertionError(errorPrefix + "Called @NotNull parameter with null");
        }
    }
}
