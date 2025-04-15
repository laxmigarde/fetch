package com.fetch.receipt.processor.data.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "No receipt found for that ID.")
public class NotFoundResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
