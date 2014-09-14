package ch.docksnet.verify;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationMin10;
import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stefan Zeller
 */
@SuppressWarnings("ConstantConditions")
@FixMethodOrder(MethodSorters.JVM)
public class MinAnnotationVerifierTest {
    private MinAnnotationVerifier sut;

    @Before
    public void setUp() throws Exception {
        sut = new MinAnnotationVerifier();
    }

    @Test
    public void testCanHandleAnnotation() throws Exception {
        Annotation min = getAnnotationMin10();

        boolean result = sut.canHandle(min);

        assertTrue(result);
    }

    @Test
    public void testCannotHandleAnnotation() throws Exception {
        Annotation notNull = getAnnotationNotNull();

        boolean result = sut.canHandle(notNull);

        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_call_with_wrong_annotation_throws_IllegalArgumentException() throws Exception {
        Object value = null;

        sut.assertViolation(getAnnotationNotNull(), value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_call_with_wrong_value_type_throws_IllegalArgumentException() throws Exception {
        double value = 3.14;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_null_doesnt_throw_AssertionError() throws Exception {
        Object value = null;

        sut.assertViolation(getAnnotationMin10(), value);
    }


    @Test(expected = AssertionError.class)
    public void test_call_with_long_greater_than_min_throws_AssertionError() throws Exception {
        long value = 11L;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_long_less_than_min_dont_throw_AssertionError() throws Exception {
        long value = 9L;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_int_greater_than_min_throws_AssertionError() throws Exception {
        int value = 11;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_int_less_than_min_dont_throw_AssertionError() throws Exception {
        int value = 9;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_short_greater_than_min_throws_AssertionError() throws Exception {
        short value = 11;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_short_less_than_min_dont_throw_AssertionError() throws Exception {
        short value = 9;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_byte_greater_than_min_throws_AssertionError() throws Exception {
        byte value = 11;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_byte_less_than_min_dont_throw_AssertionError() throws Exception {
        byte value = 9;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_BigDecimal_greater_than_min_throws_AssertionError() throws Exception {
        BigDecimal value = BigDecimal.valueOf(11);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_BigDecimal_less_than_min_dont_throw_AssertionError() throws Exception {
        BigDecimal value = BigDecimal.valueOf(9);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_BigInteger_greater_than_min_throws_AssertionError() throws Exception {
        BigInteger value = BigInteger.valueOf(11);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_BigInteger_less_than_min_dont_throw_AssertionError() throws Exception {
        BigInteger value = BigInteger.valueOf(9);

        sut.assertViolation(getAnnotationMin10(), value);
    }

}