package com.example.rail.exception;

import java.util.UUID;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(UUID id) {
        super("product with id: " + id + " doesn't available");
    }
}
