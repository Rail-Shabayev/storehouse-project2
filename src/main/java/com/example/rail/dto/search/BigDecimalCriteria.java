package com.example.rail.dto.search;

import com.example.rail.model.Product;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;

public class BigDecimalCriteria extends SearchCriteria {
    private BigDecimal value;

    @Override
    public Predicate toPredicate(@Nullable Root<Product> root, CriteriaQuery<?> query, @Nullable CriteriaBuilder cb) {
        String field = this.getField();
        return switch (this.getOperation()) {
            case "LIKE", "~":
                yield cb.like(root.get(field).as(String.class), "%" + this.getValue() + "%");

            case "EQUAL", "=":
                yield cb.equal(root.get(field), this.getValue());

            case "GRATER_THAN_OR_EQ", ">=":
                yield cb.greaterThanOrEqualTo(root.get(field), this.getValue());

            case "LESS_THAN_OR_EQ", "<=":
                yield cb.lessThanOrEqualTo(root.get(field), this.getValue());

            default:
                throw new IllegalStateException("Unexpected value: " + this.getOperation());
        };
    }
}
