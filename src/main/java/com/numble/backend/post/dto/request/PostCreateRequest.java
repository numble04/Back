package com.numble.backend.post.dto.request;

import javax.validation.constraints.NotNull;

import com.numble.backend.post.domain.PostType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostCreateRequest {

	@NotNull(message = "제목은 null일 수 없습니다")
	private String title;
	@NotNull(message = "내용은 null일 수 없습니다")
	private String content;
	@NotNull(message = "게시판 유형은 null일 수 없습니다")
	private PostType type;
}
