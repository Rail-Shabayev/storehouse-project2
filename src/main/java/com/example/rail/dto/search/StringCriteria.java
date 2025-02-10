package com.example.rail.dto.search;

import com.example.rail.model.Product;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StringCriteria extends SearchCriteria {
    private String value;

    @Override
    public Predicate toPredicate(@Nullable Root<Product> root, CriteriaQuery<?> query, @Nullable CriteriaBuilder cb) {
        String field = this.getField();
        return switch (this.getOperation()) {
            case "~", "LIKE":
                yield cb.like(root.get(field).as(String.class), "%" + this.getValue() + "%");

            case "=", "GRATER_THAN_OR_EQ", ">=", "LESS_THAN_OR_EQ", "<=", "EQUAL":
                yield cb.equal(root.get(field), this.getValue());

            default:
                throw new IllegalStateException("Unexpected value: " + this.getOperation());
        };
    }
}
