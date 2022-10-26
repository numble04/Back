package com.numble.backend.user.exception;

import com.numble.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotCreatedException extends BusinessException {
    private static final String CLIENT_MESSAGE = "사용자 생성 실패";


    public UserNotCreatedException() {
        super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
