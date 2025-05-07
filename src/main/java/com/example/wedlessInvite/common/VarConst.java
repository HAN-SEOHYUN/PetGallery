package com.example.wedlessInvite.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class VarConst {
    private VarConst() {
    }

    public static final String S3_UPLOAD_FOLDER = "uploads/";
    public static final long MAX_FILE_SIZE = 1 * 1024 * 1024; // 1MB
    public static final Set<String> VALID_FILE_EXTENSIONS = new HashSet<>(Arrays.asList("png", "jpg", "jpeg"));
    public static final String DOG_CAT_API_URL = "http://localhost:5000/predict";
    public static final String SERVER_ERROR = "서버 오류가 발생했습니다.";
    public static final String CREATED_SUCCESS_MESSAGE = "등록이 완료되었습니다.";
    public static final String DELETED_SUCCESS_MESSAGE = "삭제가 성공적으로 완료되었습니다.";
    public static final String LIKE_SUCCESS_MESSAGE = "좋아요";
    public static final String LIKE_CANCEL_MESSAGE = "좋아요 취소";
    public static final String INVITATION_LIST_FETCH_SUCCESS_MESSAGE = "리스트 조회";





}
