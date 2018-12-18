package com.caiodorn.codingtests.backbase.api.account;

import com.caiodorn.codingtests.backbase.api.account.transaction.Transaction;
import com.caiodorn.codingtests.backbase.api.account.transaction.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;

@AllArgsConstructor
@RestController
@RequestMapping("/current-accounts")
public class AccountController {

    private final TransactionService transactionService;

    @GetMapping("/{id}/transactions")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieves transactions pertaining to the provided account id")
    public List<Transaction> getTransactions(@RequestHeader(value="Authorization") String token, //token is here for Swagger UI
                                          @ApiParam(example = "savings-kids-john", required = true) @PathVariable String id,
                                          @ApiParam(example = "SANDBOX_TAN") @RequestParam(required = false) String transactionType) {
        List<Transaction> transactions;

        if (hasText(transactionType)) {
            transactions = transactionService.getTransactionsByType(id, transactionType);
        } else {
            transactions = transactionService.getTransactions(id);
        }

        return transactions;
    }

    @GetMapping("/{id}/transactions/total")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieves the total amount for transaction type")
    public Map<String, BigDecimal> getTotalAmountByType(@RequestHeader(value="Authorization") String token, //token is here for Swagger UI
                                                        @ApiParam(example = "savings-kids-john", required = true) @PathVariable String id,
                                                        @ApiParam(example = "SANDBOX_TAN", required = true) @RequestParam String transactionType) {
        BigDecimal amount = transactionService.getTotalAmountByTransactionType(id, transactionType);
        Map<String, BigDecimal> amountWrapper = new HashMap<>();
        amountWrapper.put("total", amount);

        return amountWrapper;
    }

}
