package ch.docksnet.verify.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Stefan Zeller
 */
public class OtherServiceTest {
    @Test
    public void test_square() throws Exception {
        OtherService sut = new OtherService();

        long result = sut.square(5L);

        assertEquals(25L, result);
    }

    @Test(expected = NullPointerException.class)
    public void test_throw_NPE_with_null() throws Exception {
        OtherService sut = new OtherService();

        sut.square(null);
    }
}