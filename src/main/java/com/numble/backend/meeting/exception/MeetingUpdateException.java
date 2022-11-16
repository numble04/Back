package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingUpdateException extends BusinessException {

	public MeetingUpdateException(String CLIENT_MESSAGE) {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
