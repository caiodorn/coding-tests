package com.caiodorn.codingtests.backbase.api.account.transaction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionMapperTest {

    private TransactionMapper transactionMapper = new TransactionMapper();
    private ObjectMapper objectMapper;
    private JsonNode sampleTransactionResponse;

    @BeforeAll
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sample-transactions-response.txt").getFile());
        sampleTransactionResponse = objectMapper.readTree(file).get("transactions");
    }

    @Test
    public void shouldCorrectlyMapTransactionObjectFromOpenBankJsonResponse() {
        Transaction mappedTransaction = transactionMapper.fromTransactionNode(sampleTransactionResponse.get(0));
        assertAll("transaction",
                () -> assertEquals("897706c1-dcc6-4e70-9d85-8a537c7cbf3e", mappedTransaction.getId()),
                () -> assertEquals("savings-kids-john", mappedTransaction.getAccountId()),
                () -> assertEquals("savings-kids-john", mappedTransaction.getCounterpartyAccount()),
                () -> assertEquals("ALIAS_49532E", mappedTransaction.getCounterpartyName()),
                () -> assertEquals("www.example.com/img.jpg", mappedTransaction.getCounterPartyLogoPath()),
                () -> assertEquals("SANDBOX_TAN", mappedTransaction.getTransactionType()),
                () -> assertEquals("Gift", mappedTransaction.getDescription()),
                () -> assertEquals(asFormattedBigDecimal("-90.00"), mappedTransaction.getInstructedAmount()),
                () -> assertEquals("GBP", mappedTransaction.getInstructedCurrency()),
                () -> assertEquals(asFormattedBigDecimal("-90.00"), mappedTransaction.getTransactionAmount()),
                () -> assertEquals("GBP", mappedTransaction.getTransactionCurrency())
        );
    }

    @Test
    public void shouldMapNullValuesAsNull() {
        Transaction mappedTransaction = transactionMapper.fromTransactionNode(sampleTransactionResponse.get(1));
        assertAll("transaction",
                () -> assertNull(mappedTransaction.getId()),
                () -> assertNull(mappedTransaction.getAccountId()),
                () -> assertNull(mappedTransaction.getCounterpartyAccount()),
                () -> assertNull(mappedTransaction.getCounterpartyName()),
                () -> assertNull(mappedTransaction.getCounterPartyLogoPath()),
                () -> assertNull(mappedTransaction.getTransactionType()),
                () -> assertNull(mappedTransaction.getDescription()),
                () -> assertNull(mappedTransaction.getInstructedAmount()),
                () -> assertNull(mappedTransaction.getInstructedCurrency()),
                () -> assertNull(mappedTransaction.getTransactionAmount()),
                () -> assertNull(mappedTransaction.getTransactionCurrency())
        );
    }

    @Test
    public void shouldMapPlusSignAsNullForAmountFieldWithNoAmount() {
        Transaction mappedTransaction = transactionMapper.fromTransactionNode(sampleTransactionResponse.get(2));
        assertAll("transaction",
                () -> assertNull(mappedTransaction.getTransactionAmount()),
                () -> assertNull(mappedTransaction.getInstructedAmount())
        );
    }

    @Test
    public void shouldMapMinusSignAsNullForAmountFieldWithNoAmount() {
        Transaction mappedTransaction = transactionMapper.fromTransactionNode(sampleTransactionResponse.get(3));
        assertAll("transaction",
                () -> assertNull(mappedTransaction.getTransactionAmount()),
                () -> assertNull(mappedTransaction.getInstructedAmount())
        );
    }

    private BigDecimal asFormattedBigDecimal(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.UNNECESSARY);
    }

}
