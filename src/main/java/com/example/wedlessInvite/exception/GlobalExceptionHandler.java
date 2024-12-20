package com.example.wedlessInvite.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getErrorCode().getStatusCode(),
                ex.getErrorCode().getErrorMsg()
        );
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getStatusCode());
    }
}
