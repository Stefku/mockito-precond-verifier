package ch.docksnet.verify;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationNotNull;
import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationSizeMax3;
import static ch.docksnet.verify.AnnotationTestUtil.getAnnotationSizeMin1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stefan Zeller
 */
@SuppressWarnings("ConstantConditions")
@FixMethodOrder(MethodSorters.JVM)
public class SizeAnnotationVerifierTest {

    private SizeAnnotationVerifier sut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        sut = new SizeAnnotationVerifier();
    }

    @Test
    public void testCanHandleAnnotation() throws Exception {
        Annotation max = getAnnotationSizeMin1();

        boolean result = sut.canHandle(max);

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
        thrown.expectMessage("Type is not considered supported by @Size");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_null_doesnt_throw_AssertionError() throws Exception {
        Object value = null;

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    /*
     * test supported types
     */

    /*
     * String
     */

    @Test
    public void test_call_with_String_where_length_smaller_than_min_throws_AssertionError() throws Exception {
        String value = "";

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(min = 1) parameter with length 0");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_String_where_length_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        String value = "123";

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_String_where_length_greater_than_max_throws_AssertionError() throws Exception {
        String value = "1234";

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(max = 3) parameter with length 4");

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    @Test
    public void test_call_with_String_where_length_smaller_than_max_doesnt_throw_AssertionError() throws Exception {
        String value = "12";

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    /*
     * Collection
     */

    @Test
    public void test_call_with_List_where_size_smaller_than_min_throws_AssertionError() throws Exception {
        List<?> value = new ArrayList<>();

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(min = 1) parameter with length 0");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_List_where_size_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        List<?> value = Arrays.asList("1", "2", "3");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_List_where_size_greater_than_max_throws_AssertionError() throws Exception {
        List<?> value = Arrays.asList("1", "2", "3", "4");

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(max = 3) parameter with length 4");

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    @Test
    public void test_call_with_List_where_size_smaller_than_max_doesnt_throw_AssertionError() throws Exception {
        List<?> value = Arrays.asList("1", "2");

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    /*
     * Map
     */

    @Test
    public void test_call_with_Map_where_size_smaller_than_min_throws_AssertionError() throws Exception {
        Map<?, ?> value = new HashMap<>();

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(min = 1) parameter with length 0");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_Map_where_size_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        Map<Long, Long> value = createMapWithSize(3);

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_Map_where_size_greater_than_max_throws_AssertionError() throws Exception {
        Map<Long, Long> value = createMapWithSize(4);

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(max = 3) parameter with length 4");

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    @Test
    public void test_call_with_Map_where_size_smaller_than_max_doesnt_throw_AssertionError() throws Exception {
        Map<Long, Long> value = createMapWithSize(2);

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    /*
     * Array
     */

    @Test
    public void test_call_with_Array_where_length_smaller_than_min_throws_AssertionError() throws Exception {
        String[] value = new String[]{};

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(min = 1) parameter with length 0");

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_Array_where_length_greater_than_min_doesnt_throw_AssertionError() throws Exception {
        String[] value = new String[]{"1", "2", "3"};

        sut.assertViolation(getAnnotationSizeMin1(), value);
    }

    @Test
    public void test_call_with_Array_where_length_greater_than_max_throws_AssertionError() throws Exception {
        String[] value = new String[]{"1", "2", "3", "4"};

        thrown.handleAssertionErrors();
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Called @Size(max = 3) parameter with length 4");

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }

    @Test
    public void test_call_with_Array_where_length_smaller_than_max_doesnt_throw_AssertionError() throws Exception {
        String[] value = new String[]{"1", "2"};

        sut.assertViolation(getAnnotationSizeMax3(), value);
    }


    private Map<Long, Long> createMapWithSize(int size) {
        Map<Long, Long> value = new HashMap<>();
        for (long i = 0; i < size; i++) {
            value.put(i, 1L);
        }
        return value;
    }
}