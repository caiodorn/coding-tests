package com.caiodorn.codingtests.backbase.api.account.transaction;

import com.caiodorn.codingtests.backbase.api.openbank.OpenBankClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    private ObjectMapper objectMapperMock;
    private TransactionMapper transactionMapperMock;
    private OpenBankClient openBankClientMock;
    private TransactionService transactionService;
    private List<Transaction> expectedTransactions;
    private JsonNode jsonRootNodeMock;
    private JsonNode jsonArrayNode;
    private JsonNode jsonChildNode;

    @BeforeEach
    public void setup() {
        objectMapperMock = mock(ObjectMapper.class);
        transactionMapperMock = mock(TransactionMapper.class);
        openBankClientMock = mock(OpenBankClient.class);
        expectedTransactions = new ArrayList<>();
        jsonRootNodeMock = mock(JsonNode.class);
        transactionService = new TransactionService(openBankClientMock, objectMapperMock, transactionMapperMock);
        jsonArrayNode = new ObjectMapper().createArrayNode();
        jsonChildNode = new ObjectMapper().createObjectNode();
        ((ArrayNode) jsonArrayNode).add(jsonChildNode);
        Transaction transaction = new Transaction();
        transaction.setTransactionType("type");
        transaction.setTransactionAmount(BigDecimal.TEN);
        expectedTransactions.add(transaction);
        when(transactionMapperMock.fromTransactionNode(jsonChildNode)).thenReturn(transaction);
    }

    @Test
    public void shouldReturnExpectedTransactions_whenGetTransactions() throws Exception {
        String returnedJson = "";
        when(openBankClientMock.getTransactions("id")).thenReturn(returnedJson);
        when(objectMapperMock.readTree(returnedJson)).thenReturn(jsonRootNodeMock);
        when(jsonRootNodeMock.get("transactions")).thenReturn(jsonArrayNode);

        List<Transaction> returnedTransactions = transactionService.getTransactions("id");

        assertTrue(returnedTransactions.size() == 1);
        assertSame(expectedTransactions.get(0), returnedTransactions.get(0));
    }

    @Test
    public void givenIOExceptionThrownByMapper_whenGetTransactions_thenReturnEmptyList() throws Exception {
        String returnedJson = "";
        when(openBankClientMock.getTransactions("id")).thenReturn(returnedJson);
        when(objectMapperMock.readTree(returnedJson)).thenThrow(new IOException());

        List<Transaction> returnedTransactions = transactionService.getTransactions("id");

        assertTrue(returnedTransactions.isEmpty());
    }

    @Test
    public void shouldReturnExpectedTransactions_whenGetTransactionsByType() throws Exception {
        String returnedJson = "";
        when(openBankClientMock.getTransactions("id")).thenReturn(returnedJson);
        when(objectMapperMock.readTree(returnedJson)).thenReturn(jsonRootNodeMock);
        when(jsonRootNodeMock.get("transactions")).thenReturn(jsonArrayNode);

        List<Transaction> returnedTransactions = transactionService.getTransactionsByType("id", "type");

        assertTrue(returnedTransactions.size() == 1);
        assertSame(expectedTransactions.get(0), returnedTransactions.get(0));
    }

    @Test
    public void shouldReturnTotalAmount_whenGetTotalAmountByTransactionType() throws Exception {
        String returnedJson = "";
        when(openBankClientMock.getTransactions("id")).thenReturn(returnedJson);
        when(objectMapperMock.readTree(returnedJson)).thenReturn(jsonRootNodeMock);
        when(jsonRootNodeMock.get("transactions")).thenReturn(jsonArrayNode);

        BigDecimal returnedAmount = transactionService.getTotalAmountByTransactionType("id", "type");

        assertEquals(BigDecimal.TEN, returnedAmount);
    }

}
