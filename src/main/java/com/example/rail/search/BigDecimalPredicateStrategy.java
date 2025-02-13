package com.example.rail.search;

import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;

public class BigDecimalPredicateStrategy extends PredicateStrategy {
    private final AbstractCriteria<BigDecimal> criteria;

    public BigDecimalPredicateStrategy(AbstractCriteria<BigDecimal> criteria) {
        this.criteria = criteria;
    }

    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<BigDecimal> bigDecimal = root.get(criteria.getField());
        BigDecimal value = criteria.getValue();
        return switch (criteria.getOperation()) {
            case LIKE:
                yield cb.and(cb.lessThanOrEqualTo(bigDecimal, value.add(BigDecimal.valueOf(5.00))),
                        cb.greaterThanOrEqualTo(bigDecimal, value.add(BigDecimal.valueOf(-5.00))));
            case EQUAL:
                yield cb.equal(bigDecimal, value);
            case GRATER_THAN_OR_EQ:
                yield cb.greaterThanOrEqualTo(bigDecimal, value);
            case LESS_THAN_OR_EQ:
                yield cb.lessThanOrEqualTo(bigDecimal, value);
        };
    }
}
