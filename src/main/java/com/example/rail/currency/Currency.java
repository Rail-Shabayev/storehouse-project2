package com.example.rail.currency;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum Currency {
    USD,
    CNY,
    EUR,
    RUB
}
