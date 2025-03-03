package com.example.rail.dto.order;

import com.example.rail.dto.product.ProductInOrderDto;
import com.example.rail.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddOrderDto {
    private OrderStatus status;
    private String deliveryAddress;
    private List<ProductInOrderDto> products;
}