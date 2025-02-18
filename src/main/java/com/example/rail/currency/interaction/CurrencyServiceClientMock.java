package com.example.rail.currency.interaction;

import com.example.rail.currency.ExchangeRate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
@ConditionalOnProperty(name = "rest.currency-service.mock.enabled")
public class CurrencyServiceClientMock implements CurrencyServiceClient {

    @Override
    public ExchangeRate getCurrencies() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCNY(getRnmNum());
        exchangeRate.setUSD(getRnmNum());
        exchangeRate.setEUR(getRnmNum());
        return exchangeRate;
    }

    private static BigDecimal getRnmNum() {
        return BigDecimal.valueOf((int) (Math.random() * 101));
    }
}
