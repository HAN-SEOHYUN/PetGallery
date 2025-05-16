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
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "비밀번호는 4~8자 이내여야 합니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다."),
    ALREADY_LIKED_MESSAGE(HttpStatus.BAD_REQUEST, "좋아요를 누른 게시글입니다."),
    NOT_LIKED_YET_MESSAGE(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않은 상태입니다."),
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    ;

    private final HttpStatus statusCode;
    private final String errorMsg;
}
