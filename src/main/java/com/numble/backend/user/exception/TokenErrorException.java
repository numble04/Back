package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class TokenErrorException extends BusinessException {
	private static final String CLIENT_MESSAGE = "토큰 에러가 발생하였습니다";

	public TokenErrorException() {super(CLIENT_MESSAGE, HttpStatus.FORBIDDEN);}
}
