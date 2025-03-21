package com.example.rail.kafka;

import com.example.rail.kafka.event.EventSource;
import com.example.rail.kafka.event.KafkaEvent;
import com.example.rail.kafka.handler.EventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    private final Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "test_topic", containerFactory = "kafkaListenerContainerFactoryString")
    public void listenTestTopic(String message) {
        log.info(message);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final KafkaEvent eventSource = objectMapper.readValue(message, KafkaEvent.class);
            log.info("EventSource: {}", eventSource);

            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handleEvent(eventSource);

        } catch (JsonProcessingException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}
