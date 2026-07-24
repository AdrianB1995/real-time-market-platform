package com.example.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Instant;

public class EventTimestampValidator implements ConstraintValidator<EventTimestamp, Instant> {

    @Override
    public boolean isValid(Instant value, ConstraintValidatorContext context) {
        Instant now = java.time.Instant.now();
        Instant maxFutureTime = now.plusSeconds(60);
        return !value.isAfter(maxFutureTime);
    }
}
