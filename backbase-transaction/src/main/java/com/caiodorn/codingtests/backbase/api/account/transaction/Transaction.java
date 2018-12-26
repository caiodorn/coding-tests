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

    private String id;
    private String accountId;
    private String counterpartyAccount;
    private String counterpartyName;
    private String counterPartyLogoPath;
    private BigDecimal instructedAmount;
    private String instructedCurrency;
    private BigDecimal transactionAmount;
    private String transactionCurrency;
    private String transactionType;
    private String description;

}
