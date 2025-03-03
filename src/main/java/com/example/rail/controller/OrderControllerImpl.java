package com.example.rail.controller;

import com.example.rail.dto.order.CreateOrderDto;
import com.example.rail.dto.order.EditOrderStatusDto;
import com.example.rail.dto.order.OrderResponseDto;
import com.example.rail.dto.order.UpdateOrderDto;
import com.example.rail.mapper.OrderMapper;
import com.example.rail.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderControllerImpl implements OrderController {
    private final OrderServiceImpl orderServiceImpl;
    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    public OrderResponseDto findOrder(@RequestHeader("CustomerId") Long customerId, @PathVariable UUID id) {
        return orderMapper.toOrderResponseDto(orderServiceImpl.findOrder(customerId, id));
    }

    @PostMapping
    public UUID createOrder(@RequestHeader("CustomerId") Long customerId, @RequestBody CreateOrderDto createOrderDto) {
        return orderServiceImpl.addOrder(customerId, orderMapper.toAddOrderDto(createOrderDto));
    }

    @PatchMapping("/{id}")
    public UUID editOrder(@RequestHeader("CustomerId") Long customerId, @PathVariable UUID id,
                          @RequestBody UpdateOrderDto updateOrderDto) {
        return orderServiceImpl.editOrder(customerId, id, orderMapper.toEditOrderDto(updateOrderDto));
    }

    @PatchMapping("/{id}/orderStatus")
    public void editOrderStatus(@PathVariable UUID id, @RequestBody EditOrderStatusDto editOrderStatusDto) {
        orderServiceImpl.editOrderStatus(id, editOrderStatusDto);
    }

    @PostMapping("/{id}/confirm")
    public void confirmOrder(@RequestHeader("CustomerId") Long customerId, @PathVariable UUID id) {
        orderServiceImpl.confirmOrder(customerId, id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@RequestHeader("CustomerId") Long customerId, @PathVariable UUID id) {
        orderServiceImpl.deleteOrder(customerId, id);
    }


    @PostMapping("/{orderId}/confirm")
    public void doSomething() {
        //todo fifth endpoint in the future
    }

    @PatchMapping("/{orderId}/orderStatus")
    public void doSomething2() {
        //todo sixth endpoint in the future
    }
}
