package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class DuplicateMeetingUserException extends BusinessException {
	private static final String CLIENT_MESSAGE = "이미 모임에 신청했습니다";

	public DuplicateMeetingUserException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
