package com.example.ingestion.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private List<FieldValidationError> fieldErrors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldValidationError {
        private String field;
        private String message;
        private Object rejectedValue;
    }
}

