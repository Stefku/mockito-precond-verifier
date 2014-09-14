package ch.docksnet.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.exceptions.misusing.NotAMockException;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author Stefan Zeller
 */
public class ValidationConstraintVerifierTest {

    private ClassToMock classToMock;
    private ValidationConstraintVerifier sut;

    @Before
    public void setUp() throws Exception {
        classToMock = mock(ClassToMock.class);
        sut = new ValidationConstraintVerifier();
    }

    @Test
    public void test_mock_is_accepted() throws Exception {
        Object mock = mock(List.class);
        sut.verify(mock);
    }

    @Test(expected = NotAMockException.class)
    public void test_non_mock_is_not_accepted() throws Exception {
        Object noMock = new ArrayList<String>();
        sut.verify(noMock);
    }

    @Test(expected = NotAMockException.class)
    public void test_null_is_not_accepted() throws Exception {
        sut.verify(null);
    }

    /*
     * NOT NULL
     */

    @Test
    public void testSingleNotNull() throws Exception {
        classToMock.singleString("not null");

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testSingleNull() throws Exception {
        classToMock.singleString(null);

        sut.verify(classToMock);
    }

    @Test
    public void testDoubleNotNullNotNull() throws Exception {
        classToMock.doubleString("not null", "not null");

        sut.verify(classToMock);
    }

    @Test
    public void testDoubleNullNotNull() throws Exception {
        classToMock.doubleString(null, "not null");

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testDoubleNotNullNull() throws Exception {
        classToMock.doubleString("not null", null);

        sut.verify(classToMock);
    }



    /*
     * MAX
     */


    @Test
    public void testMaxOk() throws Exception {
        classToMock.max10(10L);

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testMaxErrorLong() throws Exception {
        classToMock.max10(11L);

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testMaxErrorInt() throws Exception {
        classToMock.max10(11);

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testMaxErrorByte() throws Exception {
        classToMock.max10((byte)11);

        sut.verify(classToMock);
    }

    @Test(expected = AssertionError.class)
    public void testMaxErrorShort() throws Exception {
        classToMock.max10((short)11);

        sut.verify(classToMock);
    }








    public static interface ClassToMock {
        void singleString(@NotNull String arg1);

        void doubleString(String arg1, @NotNull String arg2);

        void max10(@Max(value = 10) long l);
    }
}