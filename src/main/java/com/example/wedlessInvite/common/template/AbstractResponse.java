package com.example.wedlessInvite.common.template;

import org.springframework.http.HttpStatus;

public abstract class AbstractResponse {
    private final boolean success;
    private final HttpStatus statusCode;
    private final String message;

    public AbstractResponse(HttpStatus statusCode, String message, boolean success) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
