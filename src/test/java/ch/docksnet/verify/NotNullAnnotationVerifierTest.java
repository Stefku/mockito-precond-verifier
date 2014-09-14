package ch.docksnet.verify;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.lang.annotation.Annotation;

import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationMax10;
import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stefan Zeller
 */
@SuppressWarnings("ConstantConditions")
@FixMethodOrder(MethodSorters.JVM)
public class NotNullAnnotationVerifierTest {

    private NotNullAnnotationVerifier sut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        sut = new NotNullAnnotationVerifier();
    }

    @Test
    public void testCanHandleAnnotation() throws Exception {
        Annotation notNull = getAnnotationNotNull();

        boolean result = sut.canHandle(notNull);

        assertTrue(result);
    }

    @Test
    public void testCannotHandleAnnotation() throws Exception {
        Annotation max = getAnnotationMax10();

        boolean result = sut.canHandle(max);

        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_call_with_wrong_annotation_throws_IllegalArgumentException() throws Exception {
        Object value = null;

        sut.assertViolation(getAnnotationMax10(), value);
    }

    @Test
    public void test_call_with_null_throws_AssertionError() throws Exception {
        Object value = null;

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @NotNull parameter with null");

        sut.assertViolation(getAnnotationNotNull(), value);
    }

    @Test
    public void test_call_with_notnull_doesnt_throw_AssertionError() throws Exception {
        Object value = "String";

        sut.assertViolation(getAnnotationNotNull(), value);
    }

}