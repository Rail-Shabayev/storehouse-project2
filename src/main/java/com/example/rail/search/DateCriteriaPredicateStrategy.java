package com.example.rail.search;

import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.model.Product;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;

public class DateCriteriaPredicateStrategy extends PredicateStrategy {
    private final AbstractCriteria<LocalDate> criteria;

    public DateCriteriaPredicateStrategy(AbstractCriteria<LocalDate> criteria) {
        this.criteria = criteria;
    }

    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<LocalDate> localDatePath = root.get(criteria.getField());
        LocalDate value = LocalDate.parse(criteria.getValue().toString());
        return switch (criteria.getOperation()) {
            case LIKE:
                yield cb.between(localDatePath, value.minusDays(3), value.plusDays(3));
            case EQUAL:
                yield cb.equal(localDatePath, value);
            case GRATER_THAN_OR_EQ:
                yield cb.greaterThanOrEqualTo(localDatePath, value);
            case LESS_THAN_OR_EQ:
                yield cb.lessThanOrEqualTo(localDatePath, value);
        };
    }
}
