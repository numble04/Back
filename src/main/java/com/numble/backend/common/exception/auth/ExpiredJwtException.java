package com.numble.backend.common.exception.auth;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.BusinessException;

public class ExpiredJwtException extends AuthenticationException {
    private static final String CLIENT_MESSAGE = "access 토큰이 만료되었습니다.";


    public ExpiredJwtException() {
        super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);
    }
}
