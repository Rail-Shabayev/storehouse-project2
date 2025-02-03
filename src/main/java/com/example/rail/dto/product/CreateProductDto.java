package com.example.rail.dto.product;

import com.example.rail.model.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductDto {
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
}
