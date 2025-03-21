package com.example.rail.kafka.handler;

import com.example.rail.dto.order.AddOrderDto;
import com.example.rail.kafka.event.CreateOrderEventData;
import com.example.rail.kafka.event.Event;
import com.example.rail.kafka.event.EventSource;
import com.example.rail.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class CreateOrderHandler implements EventHandler<CreateOrderEventData> {
    private final OrderServiceImpl orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(CreateOrderEventData eventSource) {
        return String.valueOf(
                orderService.addOrder(eventSource.getCustomerId(),
                        AddOrderDto.builder()
                                .deliveryAddress(eventSource.getDeliveryAddress())
                                .products(eventSource.getProducts())
                                .build()));
    }
}
