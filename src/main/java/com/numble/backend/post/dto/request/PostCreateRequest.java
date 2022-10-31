package com.numble.backend.post.dto.request;

import com.numble.backend.post.domain.PostType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostCreateRequest {
	private String title;
	private String content;
	private PostType type;
	private Long userId;
}
