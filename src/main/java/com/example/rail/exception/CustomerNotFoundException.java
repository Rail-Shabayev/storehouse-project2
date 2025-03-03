package com.example.rail.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super("customer with: " + customerId + " doesn't exists");
    }
}
