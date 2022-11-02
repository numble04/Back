package com.numble.backend.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    WRONG_TYPE_TOKEN("1000","token type is wrong"),
    WRONG_TOKEN("1005","token is wrong"),
    EXPIRED_TOKEN("1001","token is expired"),
    UNSUPPORTED_TOKEN("1002","token unavailable"),
    NO_TOKEN("1010","no token"),
    ACCESS_DENIED("403","access denied"),
    UNKNOWN_ERROR("9999", "error is unknown");

    private final String code;
    private final String message;

    ExceptionCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
