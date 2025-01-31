package com.example.rail.dto.product;


import com.example.rail.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private Long article;
    private String description;
    private Category category;
    private BigDecimal price;
    private int quantity;
    private LocalDateTime updatedAt;
    private LocalDate createdAt;
}
