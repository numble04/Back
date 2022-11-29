package com.numble.backend.meeting.dto.response;

import lombok.Getter;

@Getter
public class MeetingCreateResponse {
	private Long id;

	public MeetingCreateResponse(Long id) {
		this.id = id;
	}
}
