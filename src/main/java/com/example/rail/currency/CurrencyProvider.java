package com.example.rail.currency;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import static com.example.rail.currency.Currency.*;

@Data
@SessionScope
@Component
public class CurrencyProvider {
    private Currency currency = RUB;
}
