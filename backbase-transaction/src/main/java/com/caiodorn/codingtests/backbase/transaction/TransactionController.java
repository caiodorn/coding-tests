package com.caiodorn.codingtests.backbase.transaction;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TransactionController {

    private TransactionService transactionService;

    /*
    The application should expose three endpoints:
    ● Transactions list
    ● Transaction filter based on transaction type
    ● Total amount for transaction type
     */

    @GetMapping("/transactions")
    public String getTransactions() {
        return null;
    }

}
