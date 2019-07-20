package co.card.processor;

import org.junit.Before;
import org.junit.Test;

import co.card.processor.presenter.MainPresenterImpl;

import static org.junit.Assert.assertEquals;


public class CardValidationTest {

    private MainPresenterImpl mMainPresenterImpl;

    @Before
    public void setUp() {
        mMainPresenterImpl = new MainPresenterImpl();
    }

    @Test
    public void validateCardNumbers() {
        assertEquals(true, mMainPresenterImpl.isValidLuhn("371449635398431"));

        assertEquals(true, mMainPresenterImpl.isValidLuhn("6011111111111117"));

        assertEquals(true, mMainPresenterImpl.isValidLuhn("3530111333300000"));

        assertEquals(true, mMainPresenterImpl.isValidLuhn("5555555555554444"));

        assertEquals(true, mMainPresenterImpl.isValidLuhn("4111111111111111"));

        assertEquals(false, mMainPresenterImpl.isValidLuhn("2222200"));

        assertEquals(false, mMainPresenterImpl.isValidLuhn("2222222222222"));

        assertEquals(false, mMainPresenterImpl.isValidLuhn("1928374564"));
    }
}
