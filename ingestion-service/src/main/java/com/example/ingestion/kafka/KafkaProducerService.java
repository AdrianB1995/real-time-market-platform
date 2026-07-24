package com.example.ingestion.kafka;

import com.example.common.models.MarketEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, MarketEvent> kafkaTemplate;

    public void sendMessage(String topic, MarketEvent message) {
        kafkaTemplate.send(topic, message);
    }
}
