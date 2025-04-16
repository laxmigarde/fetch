package com.fetch.receipt.processor.exception;

import java.util.UUID;

public class UUIDNotFoundException extends RuntimeException {
    public UUIDNotFoundException(UUID id) {
        super("UUID " + id + " not found");
    }
}
