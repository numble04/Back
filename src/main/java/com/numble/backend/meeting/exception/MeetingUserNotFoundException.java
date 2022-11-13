package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingUserNotFoundException extends BusinessException {
	private static final String CLIENT_MESSAGE = "신청기록이 존재하지 않습니다";

	public MeetingUserNotFoundException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
