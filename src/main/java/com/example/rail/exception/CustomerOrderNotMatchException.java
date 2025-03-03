package com.example.rail.exception;

public class CustomerOrderNotMatchException extends RuntimeException {
    public CustomerOrderNotMatchException(Long id) {
        super("customer id: " + id + " doesn't match with any order");
    }
}
