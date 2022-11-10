package com.numble.backend.game.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.numble.backend.post.domain.PostType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostGameReviewRequest {

	@NotBlank(message = "내용을 입력해 주세요")
	private String content;

	@NotBlank(message = "별점을 입력해 주세요")
	private Double rate;

}
