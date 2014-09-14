package ch.docksnet.verify;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public class MinAnnotationVerifier extends MinMaxAnnotationVerifier {

    public MinAnnotationVerifier() {
        super(Min.class);
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        super.assertViolation(annotation, value);

        if (value == null) {
            // @Min considers null as valid
            return;
        }

        Min min = (Min) annotation;

        long number = toLong(value);

        if (number < min.value()) {
            throw new AssertionError(errorPrefix + String.format("Called @Min(%s) parameter with %s", min.value(), number));
        }
    }

}
