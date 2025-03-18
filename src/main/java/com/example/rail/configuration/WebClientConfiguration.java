package com.example.rail.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    private final RestConfigProperties restConfigProperties;

    @Bean
    public WebClient webClientCurrency() {
        return WebClient.builder()
                .baseUrl(restConfigProperties.getCurrencyService().getHost())
                .build();
    }

    @Bean
    public WebClient webClientAccount() {
        return WebClient.builder()
                .baseUrl(restConfigProperties.getAccountService().getHost())
                .build();
    }

    @Bean
    public WebClient webClientCrm() {
        return WebClient.builder()
                .baseUrl(restConfigProperties.getCrmService().getHost())
                .build();
    }
}
