package com.example.rail.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@Slf4j
public class TransactionalExecTimeAdvice extends TransactionSynchronizationAdapter {
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSynchronization() {
        TransactionSynchronizationManager.registerSynchronization(this);
        startTime.set(System.currentTimeMillis());

    }

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    public void afterCommit(){
        log.info("Transaction time: {}ms", System.currentTimeMillis() - startTime.get());
    }
}

