package com.caiodorn.codingtests.backbase.api.account;

import com.caiodorn.codingtests.backbase.api.account.transaction.TransactionService;
import com.caiodorn.codingtests.backbase.api.configuration.WebConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("it")
@SpringJUnitWebConfig(classes = WebConfiguration.class)
public class AccountControllerIT {
    private static final String AUTHORIZATION = "Authorization";
    private static final String VALID_USERNAME = "john doe";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_ACCOUNT = "savings-kids-john";
    private static final String INVALID_ACCOUNT = "saving-kids-jane";
    private static final String VALID_ACCOUNT_TYPE = "SANDBOX_TAN";
    private static final String INVALID_ACCOUNT_TYPE = "INVALID_ACCOUNT_TYPE";
    private static final String EMPTY_ARRAY = "[]";
    private static final String TOTAL_ZERO = "{\"total\":0}";

    private MockMvc mockMvc;
    private TransactionService transactionService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUpMockMvc(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        this.transactionService = (TransactionService) context.getBean("transactionService");
        this.objectMapper = (ObjectMapper) context.getBean("objectMapper");
    }

    @Test
    public void givenWrongPassword_whenPOSTLogin_thenReturnHttpUnauthorized() throws Exception {
        doLogin(VALID_USERNAME, "asdf").andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidUsername_whenPOSTLogin_thenReturnHttpUnauthorized() throws Exception {
        doLogin("jane doe", VALID_PASSWORD).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenUnauthenticatedUser_whenGETTransactions_thenReturnHttpForbidden() throws Exception {
        mockMvc.perform(get(String.format("/accounts/%s/transactions", VALID_ACCOUNT))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnExpectedTransactions_whenGETTransactions() throws Exception {
        String expectedJsonResponse = objectMapper.writeValueAsString(transactionService.getTransactions(VALID_ACCOUNT));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions", VALID_ACCOUNT))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expectedJsonResponse, actualJsonResponse, "Actual JSON response does not match the expected one.");
    }

    @Test
    public void givenInvalidAccount_whenGETTransactions_thenShouldReturnExpectedTransactions() throws Exception {
        String expectedJsonResponse = objectMapper.writeValueAsString(transactionService.getTransactionsByType(INVALID_ACCOUNT, VALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions?transactionType=%s", INVALID_ACCOUNT, VALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expectedJsonResponse, actualJsonResponse, "Actual JSON response does not match the expected one.");
        assertEquals(EMPTY_ARRAY, actualJsonResponse, "Expected an empty array.");
    }

    @Test
    public void shouldReturnExpectedTransactions_whenGETTransactionsByType() throws Exception {
        String expectedJsonResponse = objectMapper.writeValueAsString(transactionService.getTransactionsByType(VALID_ACCOUNT, VALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions?transactionType=%s", VALID_ACCOUNT, VALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expectedJsonResponse, actualJsonResponse, "Actual JSON response does not match the expected one.");
    }

    @Test
    public void givenInvalidAccountType_whenGETTransactionsByType_thenShouldReturnEmptyArray() throws Exception {
        String expectedJsonResponse = objectMapper.writeValueAsString(transactionService.getTransactionsByType(VALID_ACCOUNT, INVALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions?transactionType=%s", VALID_ACCOUNT, INVALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expectedJsonResponse, actualJsonResponse, "Actual JSON response does not match the expected one.");
        assertEquals(EMPTY_ARRAY, actualJsonResponse, "Expected an empty array.");
    }

    @Test
    public void shouldReturnExpectedTotal_whenGETTransactionsTotal() throws Exception {
        String totalAmount = objectMapper.writeValueAsString(transactionService.getTotalAmountByTransactionType(VALID_ACCOUNT, VALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions/total?transactionType=%s", VALID_ACCOUNT, VALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(String.format("{\"total\":%s}", totalAmount), actualJsonResponse, "Actual JSON response does not match the expected one.");
    }

    @Test
    public void givenInvalidAccountType_whenGETTransactionsTotal_thenShouldReturnZeroAsTotal() throws Exception {
        String totalAmount = objectMapper.writeValueAsString(transactionService.getTotalAmountByTransactionType(VALID_ACCOUNT, INVALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions/total?transactionType=%s", VALID_ACCOUNT, INVALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(String.format("{\"total\":%s}", totalAmount), actualJsonResponse, "Actual JSON response does not match the expected one.");
        assertEquals(TOTAL_ZERO, actualJsonResponse, "Should be '0'.");
    }

    @Test
    public void givenInvalidAccount_whenGETTransactionsTotal_thenShouldReturnZeroAsTotal() throws Exception {
        String totalAmount = objectMapper.writeValueAsString(transactionService.getTotalAmountByTransactionType(INVALID_ACCOUNT, VALID_ACCOUNT_TYPE));
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        String actualJsonResponse = mockMvc.perform(get(String.format("/accounts/%s/transactions/total?transactionType=%s", INVALID_ACCOUNT, VALID_ACCOUNT_TYPE))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(String.format("{\"total\":%s}", totalAmount), actualJsonResponse, "Actual JSON response does not match the expected one.");
        assertEquals(TOTAL_ZERO, actualJsonResponse, "Should be '0'.");
    }

    @Test
    public void givenAuthenticatedUser_whenGETTransactions_thenReturnHttpOk() throws Exception {
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        mockMvc.perform(get(String.format("/accounts/%s/transactions", VALID_ACCOUNT))
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthenticatedUserPassingBadToken_whenGETTransactions_thenReturnHttpForbidden() throws Exception {
        String token = getToken(doLogin(VALID_USERNAME, VALID_PASSWORD));

        mockMvc.perform(get(String.format("/accounts/%s/transactions", VALID_ACCOUNT))
                .header(AUTHORIZATION, token.substring(0, token.length() - 10) + "0123456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private ResultActions doLogin(String username, String password) throws Exception {
        return mockMvc.perform(post("/login").content(String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private String getToken(ResultActions resultActions) {
        return resultActions.andReturn().getResponse().getHeader(AUTHORIZATION);
    }

}
