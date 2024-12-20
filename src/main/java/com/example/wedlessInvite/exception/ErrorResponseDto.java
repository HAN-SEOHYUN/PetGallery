package com.example.wedlessInvite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    private final HttpStatus statusCode;
    private final String errorMsg;
}
