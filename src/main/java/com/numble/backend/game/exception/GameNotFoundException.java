package com.numble.backend.game.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class GameNotFoundException extends BusinessException {

	private static final String CLIENT_MESSAGE = "게임 상세 정보를 찾을 수 없습니다. (gameid 찾을 수 없음)";

	public GameNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
