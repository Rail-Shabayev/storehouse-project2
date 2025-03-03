package com.example.rail.exception;

import java.util.UUID;

public class OrderHasNotCreatedStatusException extends RuntimeException {
    public OrderHasNotCreatedStatusException(UUID id) {
        super("order with id: " + id + " not created");
    }
}
