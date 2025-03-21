package com.example.rail.kafka.event;

import com.example.rail.dto.product.ProductInOrderDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UpdateOrderEventData implements KafkaEvent {
    private Long customerId;
    private UUID orderId;
    private List<ProductInOrderDto> products;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.UPDATE_ORDER;
    }
}
