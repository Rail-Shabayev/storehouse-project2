package com.example.rail.exception;

import java.util.UUID;

public class ProductNotEnoughException extends RuntimeException {
    public ProductNotEnoughException(UUID id, int requestedAmount, int availableAmount) {
        super(String.format("Product with id [%s] not enough in warehouse. Requested amount: [%s]. " +
                "Available amount: [%s].", id, requestedAmount, availableAmount));
    }
}

