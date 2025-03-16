package com.example.rail.dto.order;

import com.example.rail.dto.customer.CustomerInfo;
import com.example.rail.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class OrderInfo {
    private UUID uuid;
    private CustomerInfo customerInfo;
    private OrderStatus status;
    private String deliveryAddress;
    private Integer quantity;
}
