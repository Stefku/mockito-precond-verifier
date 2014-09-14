package ch.docksnet.verify.example;

import ch.docksnet.verify.ValidationConstraintVerifier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainServiceTest_With_Mocking {
    private OtherService otherService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        otherService = mock(OtherService.class);
    }

    @Test
    public void test_set_OtherService_as_parameter() throws Exception {
        new MainService(otherService);
    }

    @Test
    public void test_calculate() throws Exception {
        when(otherService.square(5L)).thenReturn(25L);
        MainService sut = new MainService(otherService);

        long result = sut.square(5L);

        assertEquals(25L, result);
    }

    @Test
    public void test_exception_on_null() throws Exception {
        MainService sut = new MainService(otherService);

        try {
            sut.square(null);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Will never be thrown because the mock is not aware of precondition @NotNull");
        }
    }

    @Test
    public void test_that_constraint_verifier_throws_Error_on_null() throws Exception {
        MainService sut = new MainService(otherService);

        thrown.expect(AssertionError.class);

        sut.square(null);

        ValidationConstraintVerifier verifier = new ValidationConstraintVerifier();
        verifier.verifyConstraints(otherService);
    }
}