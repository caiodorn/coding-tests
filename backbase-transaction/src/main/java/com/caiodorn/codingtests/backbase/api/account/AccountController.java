package com.caiodorn.codingtests.backbase.api.account;

import com.caiodorn.codingtests.backbase.api.transaction.Transaction;
import com.caiodorn.codingtests.backbase.api.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/current-accounts")
public class AccountController {

    private TransactionService transactionService;

    @GetMapping("/{id}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions(@PathVariable String id,
                                             @RequestParam(required = false) String transactionType) {
        List<Transaction> transactions;

        if (transactionType != null) {
            transactions = transactionService.getTransactionsByType(id, transactionType);
        } else {
            transactions = transactionService.getTransactions(id);
        }

        return transactions;
    }

}
