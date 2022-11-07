package com.numble.backend.post.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.numble.backend.post.domain.PostType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostCreateRequest {

	@NotBlank(message = "제목을 입력해 주세요")
	@Size(min = 0, max=50, message = "제목은 50자 이내로 입력해 주세요")
	private String title;

	@NotBlank(message = "내용을 입력해 주세요")
	private String content;

	@NotNull(message = "게시판 유형을 확인해 주세요")
	private PostType type;
}
