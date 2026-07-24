package com.example.processing.kafka;

import com.example.common.models.MarketEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics="market-events", groupId="processing-service-group")
    public void consume(MarketEvent message) {
        System.out.println("Received Message: " + message);
    }
}
