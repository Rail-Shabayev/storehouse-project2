package com.example.rail.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private String exceptionName;
    private String exceptionClass;
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public ErrorMessage(String exceptionName, String exceptionClass, String exceptionMessage) {
        this.exceptionName = exceptionName;
        this.exceptionClass = exceptionClass;
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }
}
