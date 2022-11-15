package com.numble.backend.meeting.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingUpdateRequest {
	@NotBlank(message = "제목은 공백일 수 없습니다")
	private String title;

	@NotBlank(message = "내용은 공백일 수 없습니다")
	private String content;

	@NotNull(message = "인원수를 입력해 주세요")
	private Integer capacity;

	private String kakaoUrl;

	@NotNull(message = "날짜와 시간을 입력해 주세요")
	private LocalDateTime day;

	@NotNull(message = "소요시간을 입력해 주세요")
	private Integer time;

	@NotNull(message = "비용을 입력해 주세요")
	private Integer cost;

	@NotNull(message = "보드게임 카페를 알려주세요")
	private Long cafeId;
}
