package com.example.rail.kafka.handler;

import com.example.rail.dto.order.EditOrderDto;
import com.example.rail.kafka.event.Event;
import com.example.rail.kafka.event.EventSource;
import com.example.rail.kafka.event.UpdateOrderEventData;
import com.example.rail.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class UpdateOrderHandler implements EventHandler<UpdateOrderEventData> {
    private final OrderServiceImpl orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.UPDATE_ORDER.equals(eventSource.getEvent());

    }

    @Override
    public String handleEvent(UpdateOrderEventData eventSource) {
        return String.valueOf(
                orderService.editOrder(eventSource.getCustomerId(), eventSource.getOrderId(), EditOrderDto.builder()
                        .products(eventSource.getProducts())
                        .build()));
    }
}
