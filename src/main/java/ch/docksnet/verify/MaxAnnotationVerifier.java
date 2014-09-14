package ch.docksnet.verify;

import javax.validation.constraints.Max;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Stefan Zeller
 */
public class MaxAnnotationVerifier extends BaseAnnotationVerifier {

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

    private long toLong(Object value) {
        long number;
        if (value instanceof Long) {
            number = (Long) value;
        } else if (value instanceof Integer) {
            number = ((Integer) value).longValue();
        } else if (value instanceof Short) {
            number = ((Short) value).longValue();
        } else if (value instanceof Byte) {
            number = ((Byte) value).longValue();
        } else if (value instanceof BigDecimal) {
            number = ((BigDecimal) value).longValue();
        } else if (value instanceof BigInteger) {
            number = ((BigInteger) value).longValue();
        } else {
            throw new IllegalArgumentException("Type is not considered supported by @Max: " + value.getClass().toString());
        }
        return number;
    }
}
