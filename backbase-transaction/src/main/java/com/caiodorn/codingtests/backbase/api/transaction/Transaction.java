package com.caiodorn.codingtests.backbase.api.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    String id;
    String accountId;
    String counterpartyAccount;
    String counterpartyName;
    String counterPartyLogoPath;
    BigDecimal instructedAmount;
    String instructedCurrency;
    BigDecimal transactionAmount;
    String transactionCurrency;
    String transactionType;
    String description;

}
