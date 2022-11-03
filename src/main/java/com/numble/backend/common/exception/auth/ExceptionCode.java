package com.numble.backend.common.exception.auth;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    WRONG_TYPE_TOKEN("token type is wrong"),
    WRONG_TOKEN("token is wrong"),
    EXPIRED_TOKEN("token is expired"),
    UNSUPPORTED_TOKEN("token unavailable"),
    NO_TOKEN("no token");

    private final String message;

    ExceptionCode(final String message) {
        this.message = message;
    }
}
