package com.example.rail.search;

import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StringCriteriaPredicateStrategy extends PredicateStrategy {
    private final AbstractCriteria<String> criteria;

    public StringCriteriaPredicateStrategy(AbstractCriteria<String> criteria) {
        this.criteria = criteria;
    }

    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<String> stringPath = root.get(criteria.getField());
        String value = criteria.getValue();
        return switch (criteria.getOperation()) {
            case LIKE:
                yield cb.like(stringPath, "%" + value + "%");
            case EQUAL:
                yield cb.equal(stringPath, value);
            case LESS_THAN_OR_EQ:
                yield cb.like(stringPath, "%" + value);
            case GRATER_THAN_OR_EQ:
                yield cb.equal(stringPath, value + "%");
        };
    }
}
