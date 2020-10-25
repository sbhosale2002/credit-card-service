package com.braintree.creditcard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class CreditCardServiceApplicationTest {
    @Test
    void applicationTest() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("input.txt".getBytes());
        System.setIn(in);
        CreditCardServiceApplication.main(new String[] {});
        System.setIn(sysInBackup);
    }
}
