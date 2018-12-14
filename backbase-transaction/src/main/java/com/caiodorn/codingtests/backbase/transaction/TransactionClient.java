package com.caiodorn.codingtests.backbase.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Slf4j
@Component
public final class TransactionClient {

    private static final String TRANSACTIONS_URI = "https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions";

    private final Client client;

    public TransactionClient() {
        client = ClientBuilder.newBuilder().build();
    }

    public String getTransactions() {
        return sendRequest(TRANSACTIONS_URI);
    }

    private String sendRequest(final String uri) {
        final WebTarget target = client.target(uri);
        log.info("Sending request to... {}", uri);
        final Response response = target.request().get();
        log.info("Got HTTP Status... {}", response.getStatus());

        return response.readEntity(String.class);
    }

}
