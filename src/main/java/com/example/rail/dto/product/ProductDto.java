package com.example.rail.dto.product;


import com.example.rail.model.Category;
import jakarta.validation.constraints.*;
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
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private Long article;
    @NotNull
    private String description;
    @NotNull
    @NotBlank
    private Category category;
    @NotNull
    @NotBlank
    @DecimalMin(value = "0")
    private BigDecimal price;
    @NotNull
    @NotBlank
    @PositiveOrZero
    private int quantity;
    @NotNull
    @NotBlank
    private LocalDateTime updatedAt;
    @NotNull
    @NotBlank
    private LocalDate createdAt;
}
