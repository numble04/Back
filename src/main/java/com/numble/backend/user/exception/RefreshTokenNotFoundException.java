package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class RefreshTokenNotFoundException extends BusinessException {

	private static final String CLIENT_MESSAGE = "리프레쉬 토큰이 존재하지 않습니다";

	public RefreshTokenNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);
	}

}
