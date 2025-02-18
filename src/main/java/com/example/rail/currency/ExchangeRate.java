package com.example.rail.currency;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class ExchangeRate {
    private BigDecimal CNY;
    private BigDecimal USD;
    private BigDecimal EUR;
}
