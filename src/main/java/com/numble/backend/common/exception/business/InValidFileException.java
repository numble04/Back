package com.numble.backend.common.exception.business;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class InValidFileException extends BusinessException {
	private static final String CLIENT_MESSAGE = "파일이 유효하지 않습니다.";
	public InValidFileException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
