package com.example.processing.kafka;

import com.example.common.models.MarketEvent;
import com.example.processing.repo.MarketEntity;
import com.example.processing.repo.MarketEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaConsumerService {

    @Autowired
    private MarketEventRepository marketEventRepository;

    @KafkaListener(topics="market-events", groupId="processing-service-group")
    public void consume(MarketEvent message) {
        System.out.println("Received Message: " + message);
        MarketEntity marketEntity = MarketEntity.builder()
                .eventId(message.getEventId())
                .symbol(message.getSymbol())
                .price(message.getPrice())
                .volume(message.getVolume())
                .eventTime(message.getEventTime())
                .source(message.getSource())
                .receivedAt(Instant.now())
                .processedAt(Instant.now())
                .build();
        marketEventRepository.save(marketEntity);
    }
}
