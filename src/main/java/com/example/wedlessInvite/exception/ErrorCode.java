package com.example.wedlessInvite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXCEED_MAX_FILE_SIZE(HttpStatus.BAD_REQUEST, "File size exceeds the maximum limit of 1MB."),
    INVALID_FILE_TYPE (HttpStatus.BAD_REQUEST, "Invalid file type. Only PNG, JPG, and JPEG are allowed"),
    INVALID_PET_IMAGE (HttpStatus.BAD_REQUEST, "강아지나 고양이 사진만 등록할 수 있습니다. "),
    INVALID_NICKNAME_PATTERN(HttpStatus.BAD_REQUEST, "닉네임은 영어 대소문자와 한글만 사용할 수 있습니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "비밀번호는 4~8자 이내여야 합니다.");


    private final HttpStatus statusCode;
    private final String errorMsg;
}
