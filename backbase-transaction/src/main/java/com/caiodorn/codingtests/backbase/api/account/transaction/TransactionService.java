package com.caiodorn.codingtests.backbase.api.account.transaction;

import com.caiodorn.codingtests.backbase.api.openbank.OpenBankClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class TransactionService {

    private final OpenBankClient client;
    private final ObjectMapper objectMapper;
    private final TransactionMapper transactionMapper;

    public List<Transaction> getTransactions(String accountId) {
        String json = client.getTransactions(accountId);
        List<Transaction> transactions = new ArrayList<>();

        try {
            mapToTransactions(transactions, objectMapper.readTree(json).get("transactions"));
        } catch (IOException e) {
            log.error("An error happened while attempting to parse JSON response...", e);
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByType(String id, String type) {
        return getTransactions(id)
                .stream()
                .filter(transaction -> type.equals(transaction.getTransactionType()))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalAmountByTransactionType(String id, String type) {
        List<BigDecimal> transactionAmounts = new ArrayList<>();
        getTransactionsByType(id, type).forEach(transaction ->
             transactionAmounts.add(transaction.getTransactionAmount())
        );

        return transactionAmounts
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void mapToTransactions(List<Transaction> transactions, JsonNode transactionsNode) {
        if (transactionsNode != null) {
            transactionsNode.forEach(transactionNode ->
                    transactions.add(transactionMapper.fromTransactionNode(transactionNode))
            );
        }
    }

}
