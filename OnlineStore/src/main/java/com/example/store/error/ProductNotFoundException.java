package com.example.store.error;

import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    @Data
    @NoArgsConstructor
    public static class ProductErrorResponse {
        private int status;
        private String message;
        private long timestamp;
    }
}
