package com.numble.backend.comment.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsChildrenResponse {

	private Long parentId;

	private Long commentId;

	private String content;

	private String username;

	private boolean myComment;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDate;

	@QueryProjection
	public CommentsChildrenResponse(Long parentId, Long commentId, String content, String username, boolean myComment, LocalDateTime createDate, LocalDateTime updateDate) {
		this.parentId = parentId;
		this.commentId = commentId;
		this.content = content;
		this.username = username;
		this.myComment = myComment;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
