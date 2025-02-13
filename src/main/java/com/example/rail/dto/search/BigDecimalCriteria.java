package com.example.rail.dto.search;

import com.example.rail.search.BigDecimalPredicateStrategy;
import com.example.rail.search.PredicateStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class BigDecimalCriteria extends AbstractCriteria<BigDecimal> {
    protected String field;
    protected BigDecimal value;
    protected OperationType operation;
    @JsonIgnore
    protected PredicateStrategy strategy = new BigDecimalPredicateStrategy(this);
}
