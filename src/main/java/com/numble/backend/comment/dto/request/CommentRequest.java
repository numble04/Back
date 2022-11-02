package com.numble.backend.comment.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRequest {
	private Long id;
	@NotNull(message = "댓글은 null일 수 없습니다")
	private String content;
}
