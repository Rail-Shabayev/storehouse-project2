package com.example.rail.kafka.event;

import com.example.rail.kafka.handler.CreateOrderHandler;
import com.example.rail.kafka.handler.DeleteOrderHandler;
import com.example.rail.kafka.handler.UpdateOrderHandler;
import com.example.rail.kafka.handler.UpdateOrderStatusHandler;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderHandler.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderHandler.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderHandler.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusHandler.class, name = "UPDATE_ORDER_STATUS"),
})
public interface KafkaEvent extends EventSource {
}