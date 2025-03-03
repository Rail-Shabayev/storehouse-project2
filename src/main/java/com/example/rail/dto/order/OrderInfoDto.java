package com.example.rail.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderInfoDto {
    private UUID orderId;
    private List<OrderItemDto> products;
    private BigDecimal totalPrice;
}
