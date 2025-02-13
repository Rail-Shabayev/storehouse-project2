package com.example.rail.dto.search;

import com.example.rail.search.PredicateStrategy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

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
        @JsonSubTypes.Type(value = LocalDateCriteria.class, names = {"createdAt", "updatedAt"}),
        @JsonSubTypes.Type(value = BigDecimalCriteria.class, names = {"price"}),
})
public abstract class AbstractCriteria<T> {
    protected String field;
    protected T value;
    protected OperationType operation;
    protected PredicateStrategy strategy;
}
