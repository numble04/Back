package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingFullException extends BusinessException {

	private static final String CLIENT_MESSAGE = "모임 정원이 초과 하였습니다";

	public MeetingFullException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
