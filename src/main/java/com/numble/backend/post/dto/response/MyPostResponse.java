package com.numble.backend.post.dto.response;

import javax.persistence.Column;

import com.numble.backend.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPostResponse {

	private Long id;
	private String title;
	private String thumbnail;


	public static MyPostResponse toDto(Post post) {
		if (post == null) {
			return null;
		}

		MyPostResponseBuilder myPostResponse = MyPostResponse.builder();

		myPostResponse.id(post.getId());
		myPostResponse.title(post.getTitle());

		if (post.getImages().size()==0) {
			myPostResponse.thumbnail(null);
		} else {
			myPostResponse.thumbnail(post.getImages().get(0).getUrl());
		}

		return myPostResponse.build();
	}

}
