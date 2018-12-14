package com.caiodorn.codingtests.backbase.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionClient client;

    @Override
    public List<Transaction> getTransactions() {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return null;
    }

    private Transaction fromJsonString(String json) {
        /*
        Backbase field          Open bank field
        ● id                    ● id
        ● accountId             ● this_account.id
        ● counterpartyAccount   ● other_account.number
        ● counterpartyName      ● other_account.holder.name
        ● counterPartyLogoPath  ● other_account.metadata.image_URL
        ● instructedAmount      ● details.value.amount
        ● instructedCurrency    ● details.value.currency
        ● transactionAmount     ● details.value.amount
        ● transactionCurrency   ● details.value.currency
        ● transactionType       ● details.type
        ● description           ● details.description
         */

        return null;
    }

}
