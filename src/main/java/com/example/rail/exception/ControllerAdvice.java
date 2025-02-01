package com.example.rail.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorMessage handleArticleAlreadyExistsException(ArticleAlreadyExistsException exception) {
        return new ErrorMessage(CONFLICT.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "ArticleAlreadyExists");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handleRuntimeException(RuntimeException exception) {
        return new ErrorMessage(BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "Runtime error");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorMessage handleProductNotFound(ProductNotFoundException exception) {
        return new ErrorMessage(NOT_FOUND.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "Not found error ");
    }
}

