package com.example.rail.kafka.handler;

import com.example.rail.kafka.event.DeleteOrderEventData;
import com.example.rail.kafka.event.Event;
import com.example.rail.kafka.event.EventSource;
import com.example.rail.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class DeleteOrderHandler implements EventHandler<DeleteOrderEventData> {
    private final OrderServiceImpl orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(DeleteOrderEventData eventSource) {
        orderService.deleteOrder(eventSource.getCustomerId(), eventSource.getOrderId());
        return "order deleted";
    }
}
