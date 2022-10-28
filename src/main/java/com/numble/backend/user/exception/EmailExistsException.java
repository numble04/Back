package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.BusinessException;

public class EmailExistsException extends BusinessException {

	private static final String CLIENT_MESSAGE = "이메일이 존재합니다.";

	public EmailExistsException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
