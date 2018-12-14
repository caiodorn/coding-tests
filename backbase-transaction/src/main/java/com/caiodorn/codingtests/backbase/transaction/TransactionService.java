package com.caiodorn.codingtests.backbase.transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions();
    List<Transaction> getTransactionsByType(String type);
}
