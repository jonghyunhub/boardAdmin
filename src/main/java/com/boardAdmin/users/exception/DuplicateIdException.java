package com.boardAdmin.users.exception;

import com.boardAdmin.common.exception.DomainException;
import com.boardAdmin.common.exception.ErrorCode;

public class DuplicateIdException extends DomainException {
    public DuplicateIdException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
