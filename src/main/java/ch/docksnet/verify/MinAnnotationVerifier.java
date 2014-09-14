package ch.docksnet.verify;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Stefan Zeller
 */
public class MinAnnotationVerifier implements AnnotationVerifier {
    @Override
    public boolean canHandle(Annotation annotation) {
        if (annotation instanceof Min) {
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
            // @Min considers null as valid
            return;
        }

        Min min = (Min) annotation;

        long number = toLong(value);

        if (number > min.value()) {
            throw new AssertionError(errorPrefix + String.format("Called @Min(%s) parameter with %s", min.value(), number));
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
            throw new IllegalArgumentException("Type is not considered supported by @Min: " + value.getClass().toString());
        }
        return number;
    }
}
