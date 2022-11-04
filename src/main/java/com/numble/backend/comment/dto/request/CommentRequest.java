package com.numble.backend.comment.dto.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.post.domain.Post;
import com.numble.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRequest {
	private Long id;

	@NotNull(message = "댓글은 null일 수 없습니다")
	private String content;

	public static Comment toEntity(CommentRequest dto, Post post, Comment comment, User user) {
		if (dto == null && post == null && comment == null && user == null) {
			return null;
		}

		Comment.CommentBuilder comment1 = Comment.builder();

		comment1.content(dto.getContent());
		comment1.post(post);
		comment1.parent(comment);
		comment1.user(user);

		return comment1.build();
	}
}
