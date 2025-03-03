package com.example.rail.service;

import com.example.rail.dto.order.AddOrderDto;
import com.example.rail.dto.order.EditOrderDto;
import com.example.rail.dto.order.EditOrderStatusDto;
import com.example.rail.dto.order.OrderInfoDto;

import java.util.UUID;

public interface OrderService {
    public OrderInfoDto findOrder(Long customerId, UUID id);

    public UUID addOrder(Long customerId, AddOrderDto createOrderDto);

    public UUID editOrder(Long customerId, UUID id, EditOrderDto editOrderDto);

    public void deleteOrder(Long customerId, UUID id);

    public void editOrderStatus(UUID id, EditOrderStatusDto editOrderStatusDto);

    public void confirmOrder(Long customerId, UUID id);
}
