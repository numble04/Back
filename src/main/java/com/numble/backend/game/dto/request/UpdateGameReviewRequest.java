package com.numble.backend.game.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateGameReviewRequest {
	@NotBlank(message = "내용을 입력해 주세요")
	private String content;

	@NotBlank(message = "별점을 입력해 주세요")
	private Double rate;
}
