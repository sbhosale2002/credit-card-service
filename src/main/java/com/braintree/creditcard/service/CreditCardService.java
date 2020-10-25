package com.braintree.creditcard.service;

import com.braintree.creditcard.models.CreditCard;
import com.braintree.creditcard.utils.CardActionEnum;
import com.braintree.creditcard.utils.CreditCardConstants;
import com.braintree.creditcard.utils.CreditCardHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeMap;

@Service
@Slf4j
public class CreditCardService {
    private SortedMap<String, CreditCard> cardTransactions = new TreeMap<String, CreditCard>();
    private static final String ERROR = "error";

    /**
     * Handles Adding new card, charging card and crediting card actions
     * @param cardAction
     */
    public void processCardAction(String cardAction) {
        String[] inputArr = cardAction.split(" ");

        switch (CardActionEnum.valueOf(inputArr[0].toUpperCase())) {
            case ADD:
                addCard(inputArr[1], inputArr[2], inputArr[3].substring(1));
                break;
            case CHARGE:
                chargeCard(inputArr[1], new Integer(inputArr[2].substring(1)));
                break;
            case CREDIT:
                creditCard(inputArr[1], new Integer(inputArr[2].substring(1)));
                break;

        }

    }

    /**
     * Adds card data if card number is valid
     * If card number is invalid, sets balance to error
     * @param clientName
     * @param cardNumber
     * @param creditLimit
     */
    public void addCard(String clientName, String cardNumber, String creditLimit) {
        String balance = "0";
        if (CreditCardHelper.validateCardNumber(cardNumber) == false) {
            balance = ERROR;
        }

        CreditCard card = CreditCard.builder()
                .clientName(clientName)
                .cardNumber(cardNumber)
                .balance(balance)
                .creditLimit(new Integer(creditLimit)).build();

        cardTransactions.put(clientName, card);
    }

    /**
     * Charges card with chargeAmount if card number is valid
     * If card number is invalid, it ignores the transaction
     * If charge amount > remaining credit limit, ignores the transaction
     * @param clientName
     * @param chargeAmount
     */
    public void chargeCard(String clientName, Integer chargeAmount) {
        CreditCard card = cardTransactions.get(clientName);

        if (!card.getBalance().equalsIgnoreCase(ERROR) &&
                (card.getCreditLimit() - Integer.parseInt(card.getBalance())) >= chargeAmount) {
                card.setBalance(String.valueOf(Integer.parseInt(card.getBalance()) + chargeAmount));
                cardTransactions.put(clientName, card);
        }
    }

    /**
     * Credits card with creditAmount if card number is valid
     * If card number is invalid, it ignores the transaction
     * @param clientName
     * @param creditAmount
     */
    public void creditCard(String clientName, Integer creditAmount) {
        CreditCard card = cardTransactions.get(clientName);
        if (!card.getBalance().equalsIgnoreCase(ERROR)) {
            card.setBalance(String.valueOf(Integer.parseInt(card.getBalance()) - creditAmount));
            cardTransactions.put(clientName, card);
        }
    }

    /**
     * Displays credit card transactions in the format client name $balance value
     * If there was error processing transactions, then displays Client Name error
     */
    public void displayCardTransactions() {
        for (String str : cardTransactions.keySet()) {
            if(!cardTransactions.get(str).getBalance().equalsIgnoreCase(ERROR)) {
                log.info(str + ": $" + cardTransactions.get(str).getBalance());
            } else {
                log.info(str + ": error");
            }
        }
    }
    public SortedMap<String, CreditCard> getCardTransactions() {
        return cardTransactions;
    }

}
