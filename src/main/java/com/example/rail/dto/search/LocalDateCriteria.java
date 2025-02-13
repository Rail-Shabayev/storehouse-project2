package com.example.rail.dto.search;

import com.example.rail.search.DateCriteriaPredicateStrategy;
import com.example.rail.search.PredicateStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class LocalDateCriteria extends AbstractCriteria<LocalDate> {
    protected String field;
    protected LocalDate value;
    protected OperationType operation;
    @JsonIgnore
    protected PredicateStrategy strategy = new DateCriteriaPredicateStrategy(this);
}
