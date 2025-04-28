package com.example.wedlessInvite.common.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

public class SuccessResponse<T> extends AbstractResponse {
    private final T data;

    public SuccessResponse(HttpStatus statusCode, String message, T data) {
        super(statusCode, message, true);  // success 값은 항상 true
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
