package com.example.rail.kafka.handler;

import com.example.rail.dto.order.EditOrderStatusDto;
import com.example.rail.kafka.event.Event;
import com.example.rail.kafka.event.EventSource;
import com.example.rail.kafka.event.UpdateOrderStatusEventData;
import com.example.rail.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class UpdateOrderStatusHandler implements EventHandler<UpdateOrderStatusEventData> {
    private final OrderServiceImpl orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());

    }

    @Override
    public String handleEvent(UpdateOrderStatusEventData eventSource) {
        orderService.editOrderStatus(eventSource.getUuid(),
                new EditOrderStatusDto(eventSource.getStatus()));
        return "order status has changed😀";
    }
}
