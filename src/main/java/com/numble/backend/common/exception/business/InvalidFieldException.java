package com.numble.backend.common.exception.business;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class InvalidFieldException extends BusinessException {
	public InvalidFieldException(final String clientMessage) {
		super(clientMessage, HttpStatus.BAD_REQUEST);
	}
}
