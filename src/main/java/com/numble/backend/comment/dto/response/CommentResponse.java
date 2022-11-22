package com.numble.backend.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {
	private Long id;
	private String content;
	private Long postId;
}
