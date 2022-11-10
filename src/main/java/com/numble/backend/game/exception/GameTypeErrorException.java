package com.numble.backend.game.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class GameTypeErrorException extends BusinessException {

	private static final String CLIENT_MESSAGE = "게임 필터링 값이 올바르지 않습니다.";

	public GameTypeErrorException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
