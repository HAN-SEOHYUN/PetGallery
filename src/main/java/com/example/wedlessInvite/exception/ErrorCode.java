package com.example.wedlessInvite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXCEED_MAX_FILE_SIZE(HttpStatus.BAD_REQUEST, "File size exceeds the maximum limit of 1MB."),
    INVALID_FILE_TYPE (HttpStatus.BAD_REQUEST, "Invalid file type. Only PNG, JPG, and JPEG are allowed");

    private final HttpStatus statusCode;
    private final String errorMsg;
}
