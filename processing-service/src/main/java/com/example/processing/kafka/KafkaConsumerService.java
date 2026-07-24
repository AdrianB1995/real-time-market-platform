package com.example.processing.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics="market-events", groupId="processing-service-group")
    public void consume(String message) {
        System.out.println("Received Message: " + message);
    }
}
