package com.numble.backend.meeting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MeetingUserResponse {

	private Long id;
	private String nickname;
	private String img;
	private String region;

	@JsonProperty(value = "isApproved")
	private Boolean isApproved;
	@JsonProperty(value = "isLeader")
	private Boolean isLeader;

	@QueryProjection
	public MeetingUserResponse(Long id, String nickname, String img, String region, Boolean isApproved,
		Boolean isLeader) {
		this.id = id;
		this.nickname = nickname;
		this.img = img;
		this.region = region;
		this.isApproved = isApproved;
		this.isLeader = isLeader;
	}
}
