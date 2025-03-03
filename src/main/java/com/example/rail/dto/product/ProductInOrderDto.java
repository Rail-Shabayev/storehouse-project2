package com.example.rail.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductInOrderDto {
    private UUID uuid;
    private int quantity;
}