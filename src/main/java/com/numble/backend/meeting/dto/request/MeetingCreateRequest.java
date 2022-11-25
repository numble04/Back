package com.numble.backend.meeting.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingCreateRequest {

	@NotBlank(message = "제목은 공백일 수 없습니다")
	@Size(min = 2, max = 100, message = "제목은 최소 2자에 최대 100자 입니다.")
	private String title;

	@NotBlank(message = "내용은 공백일 수 없습니다")
	private String content;

	@NotNull(message = "인원수를 입력해 주세요")
	@Range(min = 2, max = 100, message = "최소 2명 부터 100명까지 가능합니다.")
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
