package com.numble.backend.game.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class GameReviewExistException extends BusinessException {
	private static final String CLIENT_MESSAGE = "이미 해당 게임에 대한 리뷰를 작성하였습니다.";

	public GameReviewExistException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
