package com.example.ingestion.models;

import com.example.ingestion.validator.EventTimestamp;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MarketEvent (
        @NotNull(message = "Event ID cannot be null")
        UUID eventId,

        @Size(min = 1, max = 10, message = "Symbol must be between 1 and 10 characters")
        @Pattern(regexp = "^[A-Z]*$", message = "Symbol must contain only uppercase letters")
        String symbol,

        @Min(value = 0, message = "Price must be greater than zero")
        BigDecimal price,

        @Min(value = 0, message = "Volume must not be negative")
        long volume,

        @NotNull
        @EventTimestamp
        Instant eventTime,

        @NotBlank(message = "Source must not be blank")
        String source
) {}