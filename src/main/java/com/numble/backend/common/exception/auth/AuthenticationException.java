package com.numble.backend.common.exception.auth;

import org.springframework.http.HttpStatus;

public abstract class AuthenticationException extends RuntimeException{
	private final String clientMessage;
	private final HttpStatus httpStatus;

	protected AuthenticationException (final String clientMessage,
		final HttpStatus httpStatus) {
		super(clientMessage);
		this.clientMessage = clientMessage;
		this.httpStatus = httpStatus;
	}
}
