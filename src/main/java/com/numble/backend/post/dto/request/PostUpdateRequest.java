package com.numble.backend.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostUpdateRequest {
	private String title;
	private String content;
	private Integer type;
	private Long userId;
}
