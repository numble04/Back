package com.numble.backend.meeting.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class MeetingLeaderException extends BusinessException {
	private static final String CLIENT_MESSAGE = "방장은 모임을 나갈 수 없습니다. 모임을 삭제해 주세요";

	public MeetingLeaderException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
