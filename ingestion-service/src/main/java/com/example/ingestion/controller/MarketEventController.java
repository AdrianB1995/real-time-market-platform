package com.example.ingestion.controller;

import com.example.ingestion.models.MarketEvent;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
public class MarketEventController {

    @PostMapping("/market-events")
    public ResponseEntity<String> handleMarketEvent(@Valid @RequestBody MarketEvent marketEvent) {
        return ResponseEntity.ok("Market event processed successfully");
    }
}
