package com.example.rail.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(UUID id) {
        super("order with id: " + id + " doesn't exists");
    }
}
