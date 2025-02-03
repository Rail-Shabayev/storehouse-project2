package com.example.rail.dto.product;

import com.example.rail.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateProductDto {
    @NotBlank(message = "Name must not be blank")
    @Length(max = 60, message = "Name can't be more than 255 characters")
    private String name;

    @NotNull
    private Long article;

    @Length(max = 255, message = "Description can't be more than 255 characters")
    private String description;

    @NotNull
    private Category category;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "1", message = "Price can't be less than 1")
    private BigDecimal price;

    @NotNull(message = "Quantity must not be null")
    @PositiveOrZero(message = "Quantity must be more or equal zero")
    private int quantity;
    private LocalDateTime updatedAt;
    private LocalDate createdAt;
}
