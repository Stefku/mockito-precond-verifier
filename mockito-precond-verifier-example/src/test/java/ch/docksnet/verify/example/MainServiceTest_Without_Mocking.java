package ch.docksnet.verify.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainServiceTest_Without_Mocking {

    private OtherService otherService;

    @Before
    public void setUp() throws Exception {
        otherService = new OtherService();
    }

    @Test
    public void test_set_OtherService_as_parameter() throws Exception {
        new MainService(otherService);
    }

    @Test
    public void test_calculate() throws Exception {
        MainService sut = new MainService(otherService);

        long result = sut.square(5L);

        assertEquals(25L, result);
    }

    @Test(expected = NullPointerException.class)
    public void test_exception_on_null() throws Exception {
        MainService sut = new MainService(otherService);

        sut.square(null);
    }
}