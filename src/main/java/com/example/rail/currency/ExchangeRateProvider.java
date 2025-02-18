package com.example.rail.currency;

import com.example.rail.currency.interaction.CurrencyServiceClientImpl;
import com.example.rail.dto.product.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateProvider {
    ClassLoader classLoader = getClass().getClassLoader();
    private final CurrencyServiceClientImpl currencyServiceClient;
    private final CurrencyProvider currencyProvider;

    public BigDecimal getExchangeRate(Currency currency) {
        return Optional.ofNullable(getExchangeRateFromService(currency))
                .orElseGet(() -> getExchangeRateFromFile(currency));
    }

    private @Nullable BigDecimal getExchangeRateFromService(Currency currency) {
        return Optional.ofNullable(currencyServiceClient.getCurrencies())
                .map(rate -> getExchangeRateByCurrency(rate, currency))
                .orElse(null);
    }

    private BigDecimal getExchangeRateFromFile(Currency currency) {
        try {
            ExchangeRate exchangeRate = new ObjectMapper().readValue(
                    classLoader.getResourceAsStream("exchange-rate.json"), ExchangeRate.class);
            return getExchangeRateByCurrency(exchangeRate, currency);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal getExchangeRateByCurrency(ExchangeRate exchangeRate, Currency currency) {
        return switch (currency) {
            case USD -> exchangeRate.getUSD();
            case CNY -> exchangeRate.getCNY();
            case EUR -> exchangeRate.getEUR();
            case RUB -> BigDecimal.ONE;
        };
    }

    public void convertProductPrice(ProductDto productDto) {
        Currency currency = currencyProvider.getCurrency();
        BigDecimal currencyValue = getExchangeRate(currency);
        BigDecimal oldPrice = productDto.getPrice();
        BigDecimal divided = oldPrice.divide(currencyValue, 2, RoundingMode.HALF_UP);
        productDto.setPrice(divided);
        productDto.setCurrency(currency);
    }

}
