package com.example.wedlessInvite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXCEED_MAX_FILE_SIZE(HttpStatus.BAD_REQUEST, "File size exceeds the maximum limit of 1MB."),
    INVALID_FILE_TYPE (HttpStatus.BAD_REQUEST, "Invalid file type. Only PNG, JPG, and JPEG are allowed"),
    INVALID_PET_IMAGE (HttpStatus.BAD_REQUEST, "강아지나 고양이 사진만 등록할 수 있습니다. ");

    private final HttpStatus statusCode;
    private final String errorMsg;
}
