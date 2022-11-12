package com.numble.backend.meeting.dto.response;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.numble.backend.cafe.domain.Cafe;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MeetingResponse {

	private Long id;
	private String title;

	private Integer maxPersonnel;

	private Integer nowPersonnel;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime day;

	private String img;

	private String cafeName;

	private Double latitude;
	private Double longitude;

	@JsonProperty(value = "isFull")
	private Boolean isFull;

	@QueryProjection
	public MeetingResponse(Long id, String title, Integer maxPersonnel, Long nowPersonnel, LocalDateTime day, String img,
		Cafe cafe, Boolean isFull) {
		this.id = id;
		this.title = title;
		this.maxPersonnel = maxPersonnel;
		this.nowPersonnel = (int) (long) nowPersonnel;
		this.day = day;
		this.img = img;
		this.cafeName = cafe.getName();
		this.isFull = isFull;
		this.latitude = cafe.getPoint().getY();
		this.longitude = cafe.getPoint().getX();
	}
}
