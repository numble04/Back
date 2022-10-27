package com.numble.backend.user.exception;

import com.numble.backend.common.exception.BusinessException;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
	private static final String CLIENT_MESSAGE = "사용자가 존재하지 않습니다.";

	public UserNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}

