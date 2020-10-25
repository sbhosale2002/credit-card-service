package com.braintree.creditcard.service;

import com.braintree.creditcard.models.CreditCard;
import com.braintree.creditcard.service.CreditCardService;
import org.hamcrest.beans.PropertyUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditCardServiceTest {

    CreditCardService creditCardService = new CreditCardService();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

	@Test
	void valid_add_test() {
		creditCardService.processCardAction("Add Roy 4111111111111111 $800");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("0", cardTransactions.get("Roy").getBalance());
        assertEquals("4111111111111111", cardTransactions.get("Roy").getCardNumber());
        assertEquals(800, cardTransactions.get("Roy").getCreditLimit());
    }

    @Test
    void invalid_add_test() {
        creditCardService.processCardAction("Add Martin 4111111212231111 $200");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("error", cardTransactions.get("Martin").getBalance());
        assertEquals("4111111212231111", cardTransactions.get("Martin").getCardNumber());
        assertEquals(200, cardTransactions.get("Martin").getCreditLimit());
    }

    @Test
    void valid_charge_test() {
        creditCardService.processCardAction("Add Roy 4111111111111111 $800");
        creditCardService.processCardAction("Charge Roy $500");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("500", cardTransactions.get("Roy").getBalance());
    }

    @Test
    void invalid_charge_test() {
        creditCardService.processCardAction("Add Edwin 4111111212231111 $200");
        creditCardService.processCardAction("Charge Edwin 200");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("error", cardTransactions.get("Edwin").getBalance());
    }

    @Test
    void credit_limit_exceed_test() {
        creditCardService.processCardAction("Add Charles 5454545454545454 $200");
        creditCardService.processCardAction("Charge Charles $201");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("0", cardTransactions.get("Charles").getBalance());
    }

    @Test
    void valid_credit_test() {
        creditCardService.processCardAction("Add Roy 4111111111111111 $800");
        creditCardService.processCardAction("Credit Roy $100");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("-100", cardTransactions.get("Roy").getBalance());
    }

    @Test
    void invalid_credit_test() {
        creditCardService.processCardAction("Add Edwin 4111111212231111 $200");
        creditCardService.processCardAction("Credit Edwin $200");
        SortedMap<String, CreditCard> cardTransactions = creditCardService.getCardTransactions();
        assertEquals("error", cardTransactions.get("Edwin").getBalance());
    }

    @Test
    void displayTest() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        creditCardService.processCardAction("Add Lisa 5454545454545454 $800");
        creditCardService.processCardAction("Credit Lisa $400");
        creditCardService.processCardAction("Add Lory 2224522454545454 $800");
        creditCardService.displayCardTransactions();
        assertTrue( outContent.toString().trim().contains("Lisa: $-400"));
        assertTrue( outContent.toString().trim().contains("Lory: error"));
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
