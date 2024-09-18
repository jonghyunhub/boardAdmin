package com.boardAdmin.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "C003", "Internal Server Error"),
    JSON_CONVERT_ERROR(404, "C004", "convert json data error"),

    // user
    USER_PASSWORD_NOT_MATCH(400,"U001" , "유저 비밀번호가 일치하지 않습니다."),
    USER_INSERT_QUERY_ERROR(500, "U002", "유저 정보 insert 중 에러 발생"),;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}