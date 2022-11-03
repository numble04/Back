package com.numble.backend.common.exception.business;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final String clientMessage;
    private final HttpStatus httpStatus;

    protected BusinessException(final String clientMessage,
                                final HttpStatus httpStatus) {
        super(clientMessage);
        this.clientMessage = clientMessage;
        this.httpStatus = httpStatus;
    }
}
