package com.caiodorn.codingtests.backbase.api.account;

import com.caiodorn.codingtests.backbase.api.account.transaction.Transaction;
import com.caiodorn.codingtests.backbase.api.account.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.StringUtils.hasText;

@AllArgsConstructor
@RestController
@RequestMapping("/current-accounts")
public class AccountController {

    private final TransactionService transactionService;

    @GetMapping("/{id}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getTransactions(@PathVariable String id,
                                                            @RequestParam(required = false) String transactionType,
                                                            @RequestParam(required = false) String getTotalAmount) {
        List<Transaction> transactions = new ArrayList<>();
        BigDecimal total = null;

        if (hasText(transactionType)) {
            if (getTotalAmount != null) {
                total = transactionService.getTotalAmountByTransactionType(id, transactionType);
            } else {
                transactions = transactionService.getTransactionsByType(id, transactionType);
            }
        } else {
            transactions = transactionService.getTransactions(id);
        }

        return total == null ? ok(transactions) : ok(total);
    }

}
