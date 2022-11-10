package com.numble.backend.game.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class GameReviewsNotFoundException extends BusinessException {

	private static final String CLIENT_MESSAGE = "게임 리뷰를 찾을 수 없습니다";

	public GameReviewsNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}

}
