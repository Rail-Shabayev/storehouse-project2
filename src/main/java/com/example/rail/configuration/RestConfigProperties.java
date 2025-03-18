package com.example.rail.configuration;

import com.example.rail.configuration.serviceProperties.AccountServiceProperties;
import com.example.rail.configuration.serviceProperties.CrmServiceProperties;
import com.example.rail.configuration.serviceProperties.CurrencyServiceProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rest")
public class RestConfigProperties {
    private CurrencyServiceProperties currencyService;
    private AccountServiceProperties accountService;
    private CrmServiceProperties crmService;
}
