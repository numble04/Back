package com.numble.backend.common.exception;

import org.springframework.http.HttpStatus;

public class ExpiredJwtException extends BusinessException{
    private static final String CLIENT_MESSAGE = "access 토큰이 만료되었습니다.";


    public ExpiredJwtException() {
        super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);
    }
}
