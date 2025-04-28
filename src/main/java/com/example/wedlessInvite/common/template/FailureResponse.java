package com.example.wedlessInvite.common.template;

import org.springframework.http.HttpStatus;

public class FailureResponse extends AbstractResponse {
    private final Object data;

    public FailureResponse(HttpStatus statusCode, String errorMsg) {
        super(statusCode, errorMsg, false);
        this.data = null; // 실패에는 data가 없는 경우가 많아서 null 처리
    }

    public FailureResponse(HttpStatus statusCode, String errorMsg, Object data) {
        super(statusCode, errorMsg, false);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}