package com.example.rail.Integration.crm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface TinServiceClient {
    CompletableFuture<Map<String, String>> getCustomerInns(List<String> customerLogins);
}
