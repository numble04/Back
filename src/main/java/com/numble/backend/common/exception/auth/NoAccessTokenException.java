package com.numble.backend.common.exception.auth;

import org.springframework.http.HttpStatus;

public class NoAccessTokenException extends AuthenticationException {

	private static final String CLIENT_MESSAGE = "access 토큰이 존재하지 않습니다.";

	public NoAccessTokenException() {
		super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);
	}
}
