package ch.docksnet.verify;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;

/**
 * @author Stefan Zeller
 */
public class AnnotationTestUtil {
    public static Annotation getAnnotationNotNull() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withNotNull", String.class).getParameterAnnotations()[0][0];
    }

    public static Annotation getAnnotationMax10() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withMax10", Long.class).getParameterAnnotations()[0][0];
    }

    public static Annotation getAnnotationMaxMinus10() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withMaxMinus10", Long.class).getParameterAnnotations()[0][0];
    }

    public static Annotation getAnnotationMin10() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withMin10", Long.class).getParameterAnnotations()[0][0];
    }

    public static Annotation getAnnotationSizeMin1() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withSizeMin1", Long.class).getParameterAnnotations()[0][0];
    }

    public static Annotation getAnnotationSizeMax3() throws NoSuchMethodException {
        return TestInterface.class.getDeclaredMethod("withSizeMax3", Long.class).getParameterAnnotations()[0][0];
    }

    @SuppressWarnings("UnusedDeclaration")
    public interface TestInterface {
        void withNotNull(@NotNull String arg1);

        void withMax10(@Max(value = 10) Long l);

        void withMaxMinus10(@Max(value = -10) Long l);

        void withMin10(@Min(value = 10) Long l);

        void withSizeMin1(@Size(min = 1) Long l);

        void withSizeMax3(@Size(max = 3) Long l);
    }
}
