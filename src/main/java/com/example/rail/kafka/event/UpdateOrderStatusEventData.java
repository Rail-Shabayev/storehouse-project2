package com.example.rail.kafka.event;

import com.example.rail.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOrderStatusEventData implements KafkaEvent {
    private UUID uuid;
    private OrderStatus status;
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.UPDATE_ORDER_STATUS;
    }
}
