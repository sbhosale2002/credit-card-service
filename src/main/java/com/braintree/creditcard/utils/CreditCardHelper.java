package com.braintree.creditcard.utils;

import com.braintree.creditcard.models.CreditCard;

public class CreditCardHelper {

    /**
     * Validates credit card number using Luhn 10 algorithm.
     * @param number
     * @return
     */
    public static boolean validateCardNumber(String number)
    {
        int length = number.length();

        int sum = 0;
        boolean isSecond = false;
        for (int i = length - 1; i >= 0; i--)
        {

            //Get the digit at ith index
            int digit = number.charAt(i) - '0';

            //If it is the second digit, multiply it by 2
            if (isSecond == true) {
                digit = digit * 2;
            }

            // If the resulting digit is 2 digit, add the 2 numbers together
            sum += digit / 10;
            sum += digit % 10;

            isSecond = !isSecond;
        }

        //If final sum is complete divisible by 10, then return true else false
        return (sum % 10 == 0);
    }

}
