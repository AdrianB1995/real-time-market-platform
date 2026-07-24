package com.example.processing.repo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "market_event")
public class MarketEntity implements Persistable<UUID> {

    @Id
    @Column(name = "event_id")
    UUID eventId;

    @Column(name = "symbol")
    String symbol;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "volume")
    long volume;

    @Column(name = "event_time")
    Instant eventTime;

    @Column(name = "source")
    String source;

    @Column(name = "received_at")
    Instant receivedAt;

    @Column(name = "processed_at")
    Instant processedAt;

    @Transient
    @Builder.Default
    private boolean isNewField = true;

    @Override
    public UUID getId() {
        return eventId;
    }

    @Override
    public boolean isNew() {
        return isNewField;
    }

    @PostPersist
    @PostLoad
    protected void markNotNew() {
        this.isNewField = false; // Changes to false after saving or loading from DB
    }
}
