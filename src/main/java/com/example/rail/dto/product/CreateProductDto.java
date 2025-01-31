package com.example.rail.dto.product;

import com.example.rail.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductDto {
    private String name;
    private Long article;
    private String description;
    private Category category;
    private BigDecimal price;
    private int quantity;
}
