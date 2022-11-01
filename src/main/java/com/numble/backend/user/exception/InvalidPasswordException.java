package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
	private static final String CLIENT_MESSAGE = "비밀번호가 맞지 않습니다.";

	public InvalidPasswordException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
