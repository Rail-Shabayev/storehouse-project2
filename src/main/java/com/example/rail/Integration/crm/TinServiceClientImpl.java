package com.example.rail.Integration.crm;

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
public class TinServiceClientImpl implements TinServiceClient {
    private final WebClient webClientCrm;
    private final RestConfigProperties restConfigProperties;

    public CompletableFuture<Map<String, String>> getCustomerInns(List<String> customerLogins) {
        return webClientCrm
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(restConfigProperties.getCrmService().getMethods().get("get-tin"))
                        .queryParam("customerLogins", customerLogins)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .doOnSuccess(mes -> log.info("Get customers inns from Crm service"))
                .doOnError(e -> {
                    throw new RuntimeException("error in tin service");
                })
                .toFuture();
    }
}
