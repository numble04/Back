package com.numble.backend.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    WRONG_TYPE_TOKEN("1000","토큰 타입이 잘못됨"),
    WRONG_TOKEN("1005","토큰 잘못됨"),
    EXPIRED_TOKEN("1001","토큰이 만료됨"),
    UNSUPPORTED_TOKEN("1002","지원하지 않는 토큰"),
    ACCESS_DENIED("403","접근 금지"),
    UNKNOWN_ERROR("9999", "에러를 알 수 없음");

    private final String code;
    private final String message;

    ExceptionCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
