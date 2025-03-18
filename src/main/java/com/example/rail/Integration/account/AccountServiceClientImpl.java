package com.example.rail.Integration.account;

import com.example.rail.configuration.RestConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountServiceClientImpl implements AccountServiceClient {
    private final WebClient webClientAccount;
    private final RestConfigProperties restConfigProperties;

    public CompletableFuture<Map<String, String>> getCustomerAccounts(List<String> customerLogins) {
        return webClientAccount
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(restConfigProperties.getAccountService().getMethods().get("get-accountNum"))
                        .queryParam("customerLogins", customerLogins)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .doOnSuccess(mes -> log.info("Get customers accounts from Account service"))
                .doOnError(e -> {
                    throw new RuntimeException("error in account service");
                })
                .toFuture();
    }
}
