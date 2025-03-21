package com.example.rail.kafka.handler;

import com.example.rail.kafka.event.EventSource;

public interface EventHandler <T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    String handleEvent(T eventSource);
}