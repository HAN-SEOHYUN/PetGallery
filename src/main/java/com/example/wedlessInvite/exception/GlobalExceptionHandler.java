package com.example.wedlessInvite.exception;

import com.example.wedlessInvite.common.template.AbstractResponse;
import com.example.wedlessInvite.common.template.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.wedlessInvite.common.VarConst.SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<AbstractResponse> handleCustomException(CustomException ex) {
        FailureResponse errorResponse = new FailureResponse(
                ex.getErrorCode().getStatusCode(),
                ex.getErrorCode().getErrorMsg()
        );
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<AbstractResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder("필수입력값 누락: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(fieldError.getDefaultMessage())
                    .append("; ");
        }

        FailureResponse errorResponse = new FailureResponse(
                HttpStatus.BAD_REQUEST,
                errorMsg.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<AbstractResponse> handleGenericException(Exception ex) {
        FailureResponse errorResponse = new FailureResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                SERVER_ERROR
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

