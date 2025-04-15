package com.fetch.receipt.processor.data.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The receipt is invalid.")
public class BadRequestResponse {

    private String message;

    public BadRequestResponse() { }
    public BadRequestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
