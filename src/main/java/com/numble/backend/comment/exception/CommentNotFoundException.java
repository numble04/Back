package com.numble.backend.comment.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {
	private static final String CLIENT_MESSAGE = "댓글이 존재하지 않습니다.";

	public CommentNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
