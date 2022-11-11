package com.numble.backend.cafe.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class CafeNotFoundException extends BusinessException {
	private static final String CLIENT_MESSAGE = "카페가 존재하지 않습니다.";

	public CafeNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
