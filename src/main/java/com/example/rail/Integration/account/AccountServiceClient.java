package com.example.rail.Integration.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountServiceClient {
    CompletableFuture<Map<String, String>> getCustomerAccounts(List<String> customerLogins);
}
