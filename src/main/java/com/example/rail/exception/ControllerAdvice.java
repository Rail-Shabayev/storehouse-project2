package com.example.rail.exception;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorMessage handleArticleAlreadyExistsException(ArticleAlreadyExistsException exception) {
        return new ErrorMessage(exception.getClass().getSimpleName(),
                exception.getStackTrace()[0].getClassName(),
                exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handleRuntimeException(RuntimeException exception) {
        return new ErrorMessage(exception.getClass().getSimpleName(),
                exception.getStackTrace()[0].getClassName(),
                exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorMessage handleProductNotFound(ProductNotFoundException exception) {
        return new ErrorMessage(exception.getClass().getSimpleName(),
                exception.getStackTrace()[0].getClassName(),
                exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handleArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ErrorMessage(exception.getClass().getSimpleName(),
                exception.getStackTrace()[0].getClassName(),
                errors.toString());
    }
}

