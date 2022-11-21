package com.numble.backend.post.dto.response;

import com.numble.backend.post.domain.Post;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPostResponse {

	private Long id;
	private String title;
	private String thumbnail;

	@Builder
	private MyPostResponse(Long id, String title, String thumbnail) {
		this.id = id;
		this.title = title;
		this.thumbnail = thumbnail;
	}

	@QueryProjection
	public MyPostResponse(Long id, String title, Post post) {
		this.id = id;
		this.title = title;
		this.thumbnail = post.getImages().size() != 0 ? post.getImages().get(0).getUrl() : null;
	}
}
