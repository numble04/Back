package com.numble.backend.post.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class PostNotFoundException extends BusinessException {
    private static final String CLIENT_MESSAGE = "게시물이 존재하지 않습니다.";

    public PostNotFoundException() {
        super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}

