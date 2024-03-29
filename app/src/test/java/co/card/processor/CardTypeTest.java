package co.card.processor;

import org.junit.Test;

import co.card.processor.model.CardType;

import static org.junit.Assert.assertEquals;

public class CardTypeTest {

    @Test
    public void testCardType() {

        assertEquals(CardType.VISA, CardType.detect("4000056655665556"));
        assertEquals(CardType.VISA, CardType.detect("4111111111111111"));

        assertEquals(CardType.MASTERCARD, CardType.detect("5105105105105100"));
        assertEquals(CardType.MASTERCARD, CardType.detect("5200828282828210"));
        assertEquals(CardType.MASTERCARD, CardType.detect("5555555555554444"));

        assertEquals(CardType.AMERICAN_EXPRESS, CardType.detect("371449635398431"));
        assertEquals(CardType.AMERICAN_EXPRESS, CardType.detect("378282246310005"));

        assertEquals(CardType.DISCOVER, CardType.detect("6011000990139424"));
        assertEquals(CardType.DISCOVER, CardType.detect("6011111111111117"));

        assertEquals(CardType.JCB, CardType.detect("3530111333300000"));
        assertEquals(CardType.JCB, CardType.detect("3566002020360505"));

        assertEquals(CardType.UNKNOWN, CardType.detect("0000000000000000"));
    }
}
