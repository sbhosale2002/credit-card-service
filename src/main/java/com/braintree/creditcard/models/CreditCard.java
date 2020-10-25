package com.braintree.creditcard.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreditCard {
    private String clientName;
    private String cardNumber;
    private String balance;
    private Integer creditLimit;
}
