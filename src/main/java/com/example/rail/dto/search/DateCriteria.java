package com.example.rail.dto.search;

import com.example.rail.model.Product;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateCriteria extends SearchCriteria {
    private LocalDate value;

    @Override
    public Predicate toPredicate(@Nullable Root<Product> root, CriteriaQuery<?> query, @Nullable CriteriaBuilder cb) {
        String field = this.getField();
        LocalDate value = LocalDate.from(LocalDateTime.parse(
                this.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
        return switch (this.getOperation()) {
            case "LIKE", "~":
                yield cb.between(root.get(field), value.minusDays(3), value.plusDays(3));

            case "EQUAL", "=":
                yield cb.equal(root.get(field), value);

            case "GRATER_THAN_OR_EQ", ">=":
                yield cb.greaterThanOrEqualTo(root.get(field), value);

            case "LESS_THAN_OR_EQ", "<=":
                yield cb.lessThanOrEqualTo(root.get(field), value);

            default:
                throw new IllegalStateException("Unexpected value: " + this.getOperation());
        };
    }
}
