package com.numble.backend.comment.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostOneCommentResponse {
	private Long parentId;

	private Long commentId;

	private String content;

	private String nickname;

	private int likeCount;
	private String userImg;
	private boolean myComment;

	private boolean myLike;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDate;

	private List<CommentsChildrenResponse> children = new ArrayList<>();

	@QueryProjection
	public PostOneCommentResponse(Long parentId, Long commentId, String content, String nickname, int likeCount,
		String userImg, boolean myComment, boolean myLike, LocalDateTime createDate, LocalDateTime updateDate) {
		this.parentId = parentId;
		this.commentId = commentId;
		this.content = content;
		this.nickname = nickname;
		this.likeCount = likeCount;
		this.userImg = userImg;
		this.myComment = myComment;
		this.myLike = myLike;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
}
