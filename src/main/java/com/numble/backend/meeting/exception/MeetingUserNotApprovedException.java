package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingUserNotApprovedException extends BusinessException {
	private static final String CLIENT_MESSAGE = "승인된 멤버가 아닙니다";

	public MeetingUserNotApprovedException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
