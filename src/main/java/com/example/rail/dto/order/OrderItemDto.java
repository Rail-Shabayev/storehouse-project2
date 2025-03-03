package com.example.rail.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderItemDto {
    private UUID productId;
    private String name;
    private int quantity;
    private BigDecimal price;
}
