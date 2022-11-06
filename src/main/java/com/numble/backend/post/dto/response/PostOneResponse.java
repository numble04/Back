package com.numble.backend.post.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numble.backend.comment.dto.response.PostOneCommentResponse;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostOneResponse {
	private Long postId;
	private String title;
	private String content;
	private int commentCount;
	private int likeCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDate;
	private Integer viewCount;
	private String nickname;
	private String userImg;
	private boolean myPost;
	private boolean myLike;
	private List<PostOneCommentResponse> comments = new ArrayList<>();

	private List<String> images = new ArrayList<>();

	@QueryProjection
	public PostOneResponse(Long postId, String title, String content, int commentCount, int likeCount,
		LocalDateTime createDate, LocalDateTime updateDate, Integer viewCount, String nickname, String userImg,
		boolean myPost, boolean myLike) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.viewCount = viewCount;
		this.nickname = nickname;
		this.userImg = userImg;
		this.myPost = myPost;
		this.myLike = myLike;
	}

}
