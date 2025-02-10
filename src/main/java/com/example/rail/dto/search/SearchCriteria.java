package com.example.rail.dto.search;

import com.example.rail.model.Product;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "field"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringCriteria.class, names = {"name", "description"}),
        @JsonSubTypes.Type(value = DateCriteria.class, names = {"createdAt", "updatedAt"}),
        @JsonSubTypes.Type(value = BigDecimalCriteria.class, names = {"price"}),
        @JsonSubTypes.Type(value = IntCriteria.class, names = {"quantity"}),
})
public class SearchCriteria implements Specification<Product> {
    protected String field;
    protected String value;
    protected String operation;

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
