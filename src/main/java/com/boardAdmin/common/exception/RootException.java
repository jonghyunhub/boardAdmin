package com.boardAdmin.common.exception;

import lombok.Getter;

@Getter
public class RootException extends RuntimeException {

    private final ErrorCode errorCode;

    public RootException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
