package com.boardAdmin.users.exception;

import com.boardAdmin.common.exception.DomainException;

public class DuplicateIdException extends DomainException {
    public DuplicateIdException(String message) {
        super(message);
    }
}
