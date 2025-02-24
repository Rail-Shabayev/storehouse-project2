package com.example.rail.dto.product;


import com.example.rail.currency.Currency;
import com.example.rail.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ProductDto {
    private String name;
    private Long article;
    private String description;
    private Category category;
    private BigDecimal price;
    private int quantity;
    private LocalDateTime updatedAt;
    private LocalDate createdAt;
    private Currency currency;
}
