package com.example.rail.dto.order;

import com.example.rail.dto.product.ProductInOrderDto;
import com.example.rail.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateOrderDto {
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private List<ProductInOrderDto> products;
}
