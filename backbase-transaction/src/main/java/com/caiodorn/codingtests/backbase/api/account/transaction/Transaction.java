package com.caiodorn.codingtests.backbase.api.account.transaction;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
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
