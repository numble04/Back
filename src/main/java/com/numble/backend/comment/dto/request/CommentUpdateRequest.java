package com.numble.backend.comment.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentUpdateRequest {
	private Long id;

	@NotEmpty(message = "댓글은 공백일 수 없습니다")
	private String content;
}
