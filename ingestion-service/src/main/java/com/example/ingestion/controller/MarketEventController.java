package com.example.ingestion.controller;

import com.example.ingestion.kafka.KafkaProducerService;
import com.example.ingestion.models.MarketEvent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
public class MarketEventController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/market-events")
    public ResponseEntity<String> handleMarketEvent(@Valid @RequestBody MarketEvent marketEvent) {
        kafkaProducerService.sendMessage("market-events", marketEvent.toString());
        return ResponseEntity.ok("Market event processed successfully");
    }
}
