package ch.docksnet.verify;

import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author Stefan Zeller
 */
public class SizeAnnotationVerifier extends BaseAnnotationVerifier {

    protected SizeAnnotationVerifier() {
        super(Size.class);
    }

    @Override
    public void assertViolation(Annotation annotation, Object value) {
        super.assertViolation(annotation, value);

        if (value == null) {
            // @Size considers null as valid
            return;
        }

        Size size = (Size) annotation;

        long length = getLength(value);

        if (length < size.min()) {
            throw new AssertionError(errorPrefix + String.format("Called @Size(min = %s) parameter with length %s", size.min(), length));
        }
        if (length > size.max()) {
            throw new AssertionError(errorPrefix + String.format("Called @Size(max = %s) parameter with length %s", size.max(), length));
        }
    }

    private long getLength(Object value) {
        if (value instanceof String) {
            return ((String) value).length();
        } else if (value instanceof Collection) {
            return ((Collection) value).size();
        } else if (value instanceof Map) {
            return ((Map) value).size();
        } else if (value instanceof Object[]) {
            return Array.getLength(value);
        } else {
            throw new IllegalArgumentException("Type is not considered supported by @Size");
        }
    }
}
