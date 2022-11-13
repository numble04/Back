package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingNotFoundException extends BusinessException {

	private static final String CLIENT_MESSAGE = "모임이 존재하지 않습니다";

	public MeetingNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
