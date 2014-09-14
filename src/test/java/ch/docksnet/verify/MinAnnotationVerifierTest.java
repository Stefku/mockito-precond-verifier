package ch.docksnet.verify;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void test_call_with_wrong_value_type_throws_IllegalArgumentException() throws Exception {
        double value = 3.14;

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Type is not considered supported by @Min");

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_null_doesnt_throw_AssertionError() throws Exception {
        Object value = null;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    /*
     * test supported types
     */

    @Test
    public void test_call_with_long_less_than_min_throws_AssertionError() throws Exception {
        long value = 8L;

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Min(10) parameter with 8");

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_long_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        long value = 12L;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_int_less_than_min_throws_AssertionError() throws Exception {
        int value = 8;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_int_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        int value = 12;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_short_less_than_min_throws_AssertionError() throws Exception {
        short value = 8;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_short_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        short value = 12;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_byte_less_than_min_throws_AssertionError() throws Exception {
        byte value = 8;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_byte_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        byte value = 12;

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_BigDecimal_less_than_min_throws_AssertionError() throws Exception {
        BigDecimal value = BigDecimal.valueOf(8);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_BigDecimal_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        BigDecimal value = BigDecimal.valueOf(12);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test(expected = AssertionError.class)
    public void test_call_with_BigInteger_less_than_min_throws_AssertionError() throws Exception {
        BigInteger value = BigInteger.valueOf(8);

        sut.assertViolation(getAnnotationMin10(), value);
    }

    @Test
    public void test_call_with_BigInteger_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        BigInteger value = BigInteger.valueOf(12);

        sut.assertViolation(getAnnotationMin10(), value);
    }

}