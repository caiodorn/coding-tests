package com.caiodorn.codingtests.backbase.api.account;

import com.caiodorn.codingtests.backbase.api.account.transaction.Transaction;
import com.caiodorn.codingtests.backbase.api.account.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    private TransactionService transactionServiceMock;
    private AccountController accountController;
    private List<Transaction> expectedTransactions;

    @BeforeEach
    public void setup() {
        transactionServiceMock = mock(TransactionService.class);
        accountController = new AccountController(transactionServiceMock);
        expectedTransactions = new ArrayList<>();
    }

    @Test
    public void givenTransactionTypeNotProvided_whenGetTransactions_thenShouldDelegateToGetTransactions() {
        when(transactionServiceMock.getTransactions("id")).thenReturn(expectedTransactions);

        List<Transaction> returnedTransactions = accountController.getTransactions("token", "id", null);

        verify(transactionServiceMock, never()).getTransactionsByType(anyString(), anyString());
        verify(transactionServiceMock, never()).getTotalAmountByTransactionType(anyString(), anyString());
        verify(transactionServiceMock, times(1)).getTransactions("id");
        assertEquals(expectedTransactions, returnedTransactions);
    }

    @Test
    public void givenTransactionTypeProvided_whenGetTransactions_thenShouldDelegateToGetTransactionsByType() {
        when(transactionServiceMock.getTransactionsByType("id", "type")).thenReturn(expectedTransactions);

        List<Transaction> returnedTransactions = accountController.getTransactions("token", "id", "type");

        verify(transactionServiceMock, times(1)).getTransactionsByType(anyString(), anyString());
        verify(transactionServiceMock, never()).getTotalAmountByTransactionType(anyString(), anyString());
        verify(transactionServiceMock, never()).getTransactions("id");
        assertEquals(expectedTransactions, returnedTransactions);
    }

    @Test
    public void shouldReturnTotalAmount_whenGetTotalAmountByTransactionType() {
        when(transactionServiceMock.getTotalAmountByTransactionType("id", "type")).thenReturn(BigDecimal.TEN);

        Map<String, BigDecimal> expectedResponse = new HashMap<>();
        expectedResponse.put("total", BigDecimal.TEN);

        Map<String, BigDecimal> response = accountController.getTotalAmountByType("token", "id", "type");

        verify(transactionServiceMock, times(1)).getTotalAmountByTransactionType("id", "type");
        assertEquals(expectedResponse.toString(), response.toString());
    }

}
