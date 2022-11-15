package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class UserNotAuthorException extends BusinessException {
	private static final String CLIENT_MESSAGE = "작성자가 아닙니다.";

	public UserNotAuthorException() {
		super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);
	}
}
