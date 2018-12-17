package com.caiodorn.codingtests.backbase.transaction;

import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Component
public class OpenBankClient {

    private static final String TRANSACTIONS_URI = "https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/%s/public/transactions";

    private final Client client;

    public OpenBankClient() {
        client = ClientBuilder.newBuilder().build();
    }

    public String getTransactions(String accountId) {
        return sendRequest(String.format(TRANSACTIONS_URI, accountId));
    }

    private String sendRequest(String uri) {
        WebTarget target = client.target(uri);
        Response response = target.request().get();

        return response.readEntity(String.class);
    }

}
