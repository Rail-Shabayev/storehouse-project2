package com.example.rail.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAdvice {

    @Around("@annotation(com.example.rail.annotation.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object;
        try{
             object = point.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Method Name: {}. Time: {}ms", point.getSignature().getName(), endTime - startTime);
        }
        return object;
    }
}