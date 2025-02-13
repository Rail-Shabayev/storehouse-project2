package com.example.rail.dto.search;

import com.example.rail.search.PredicateStrategy;
import com.example.rail.search.StringCriteriaPredicateStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StringCriteria extends AbstractCriteria<String> {
    protected String field;
    protected String  value;
    protected OperationType operation;
    @JsonIgnore
    protected PredicateStrategy strategy = new StringCriteriaPredicateStrategy(this);
}
