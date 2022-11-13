package com.numble.backend.meeting.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.numble.backend.cafe.domain.Cafe;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingDetailResponse {
	private Long id;
	private String title;
	private String content;
	private String kakaoUrl;
	private String img;
	private Integer time;
	private Integer cost;
	private Integer maxPersonnel;
	private Integer nowPersonnel;
	private Integer likeCount;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime day;

	@JsonProperty(value = "isFull")
	private Boolean isFull;

	private String cafeName;
	private Long cafeId;
	private Double latitude;
	private Double longitude;

	private List<MeetingUserResponse> users = new ArrayList<>();

	private boolean myLike;
	private boolean myMeeting;

	@QueryProjection
	public MeetingDetailResponse(Long id, String title, String content, String kakaoUrl, String img,
		Integer time, Integer cost, Integer maxPersonnel, Long nowPersonnel, Integer likeCount, LocalDateTime day,
		Boolean isFull, Cafe cafe, boolean myLike) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.kakaoUrl = kakaoUrl;
		this.img = img;
		this.time = time;
		this.cost = cost;
		this.maxPersonnel = maxPersonnel;
		this.nowPersonnel = (int) (long) nowPersonnel;
		this.likeCount = likeCount;
		this.day = day;
		this.isFull = isFull;
		this.cafeName = cafe.getName();
		this.cafeId = cafe.getId();
		this.latitude = cafe.getPoint().getY();
		this.longitude = cafe.getPoint().getX();
		this.myLike = myLike;
	}
}
