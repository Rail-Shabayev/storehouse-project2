package com.example.rail.currency.interaction;

import com.example.rail.configuration.RestConfigProperties;
import com.example.rail.currency.ExchangeRate;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {

    private final RestConfigProperties restConfigProperties;
    private final WebClient webClient;

    @Override
    @Cacheable(value = "currencies", unless = "#result == null")
    public @Nullable ExchangeRate getCurrencies() {
        try {
            return webClient.get()
                    .uri(restConfigProperties.getMethods().get("get-currency"))
                    .retrieve()
                    .bodyToMono(ExchangeRate.class)
                    .retry(2)
                    .block();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
