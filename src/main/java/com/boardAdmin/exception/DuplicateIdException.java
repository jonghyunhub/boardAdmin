package com.boardAdmin.exception;

public class DuplicateIdException extends DomainException{
    public DuplicateIdException(String message) {
        super(message);
    }
}
