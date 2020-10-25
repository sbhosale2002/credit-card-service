package com.braintree.creditcard.utils;

import com.braintree.creditcard.utils.CreditCardHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardHelperTest {

    @Test
    public void validCardNumberTest() {
        assertEquals(true, CreditCardHelper.validateCardNumber("5454545454545454"));
    }

    @Test
    public void invalidCardNumberTest() {
        assertEquals(false, CreditCardHelper.validateCardNumber("4111111212231111"));
    }
}
