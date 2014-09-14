package ch.docksnet.verify;

import javax.validation.constraints.Max;
import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public class MaxAnnotationVerifier extends MinMaxAnnotationVerifier {

    protected MaxAnnotationVerifier() {
        super(Max.class);
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        super.assertViolation(annotation, value);

        if (value == null) {
            // @Max considers null as valid
            return;
        }

        Max max = (Max) annotation;

        long number = toLong(value);

        if (number > max.value()) {
            throw new AssertionError(errorPrefix + String.format("Called @Max(%s) parameter with %s", max.value(), number));
        }
    }

}
