package com.example.common.models;

import com.example.common.validator.EventTimestamp;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketEvent {
    @NotNull(message = "Event ID cannot be null")
    UUID eventId;

    @Size(min = 1, max = 10, message = "Symbol must be between 1 and 10 characters")
    @Pattern(regexp = "^[A-Z]*$", message = "Symbol must contain only uppercase letters")
    String symbol;

    @Min(value = 1, message = "Price must be greater than zero")
    BigDecimal price;

    @Min(value = 1, message = "Volume must not be negative")
    long volume;

    @NotNull
    @EventTimestamp
    Instant eventTime;

    @NotBlank(message = "Source must not be blank")
    String source;
}