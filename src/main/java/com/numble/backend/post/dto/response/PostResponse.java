package com.numble.backend.post.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	private PostType type;
	private String thumbnail;
	private Integer viewCount;
	private int likeCount;
	private int commentCount;
	private boolean myPost;
	private boolean myLike;
	private String nickname;
	private String userImg;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDate;

	@QueryProjection
	public PostResponse(Long id, String title, String content, PostType type, Post post, Integer viewCount,
		int likeCount, int commentCount, boolean myPost, boolean myLike, String nickname, String userImg,
		LocalDateTime createDate, LocalDateTime updateDate) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.type = type;
		this.thumbnail = post.getImages().size() != 0 ? post.getImages().get(0).getUrl() : null;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.myPost = myPost;
		this.myLike = myLike;
		this.nickname = nickname;
		this.userImg = userImg;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
}
