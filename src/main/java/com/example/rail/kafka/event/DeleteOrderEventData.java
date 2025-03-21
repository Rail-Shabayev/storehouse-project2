package com.example.rail.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteOrderEventData implements KafkaEvent {
    private Long customerId;
    private UUID orderId;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.DELETE_ORDER;
    }
}
