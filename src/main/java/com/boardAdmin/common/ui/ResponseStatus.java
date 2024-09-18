package com.boardAdmin.common.ui;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ResponseStatus {
    // client - valid, server - ok
    SUCCESS("success"),
    // client - invalid, server - ok
    FAIL("fail"),
    // client - invalid or valid, server - unexpected error
    ERROR("error");
    private final String status;

    ResponseStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status.toLowerCase();
    }

    @JsonValue
    public String toValue() {
        return this.status.toLowerCase();
    }
}