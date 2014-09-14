package ch.docksnet.verify;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Stefan Zeller
 */
public abstract class MinMaxAnnotationVerifier extends BaseAnnotationVerifier {

    protected MinMaxAnnotationVerifier(Class<?> baseAnnotation) {
        super(baseAnnotation);
    }

    protected long toLong(Object value) {
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
            throw new IllegalArgumentException("Type is not considered supported by @" + baseAnnotation.getSimpleName() + ": " + value.getClass().toString());
        }
        return number;
    }

}
