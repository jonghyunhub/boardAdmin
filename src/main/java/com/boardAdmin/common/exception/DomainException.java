package com.boardAdmin.common.exception;

public class DomainException extends RootException {

    public DomainException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
