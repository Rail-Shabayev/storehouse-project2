package com.example.rail.service;

import com.example.rail.dto.order.AddOrderDto;
import com.example.rail.dto.order.EditOrderDto;
import com.example.rail.dto.order.EditOrderStatusDto;
import com.example.rail.dto.order.OrderInfo;
import com.example.rail.dto.order.OrderInfoDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface OrderService {
    OrderInfoDto findOrder(Long customerId, UUID id);

    UUID addOrder(Long customerId, AddOrderDto createOrderDto);

    UUID editOrder(Long customerId, UUID id, EditOrderDto editOrderDto);

    void deleteOrder(Long customerId, UUID id);

    void editOrderStatus(UUID id, EditOrderStatusDto editOrderStatusDto);

    void confirmOrder(Long customerId, UUID id);

    Map<UUID, List<OrderInfo>> getProductInfo() throws ExecutionException, InterruptedException;

}
