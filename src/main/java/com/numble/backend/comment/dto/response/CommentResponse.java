package com.numble.backend.comment.dto.response;

import java.util.List;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponse {
	private Long id;
	private String content;
	private CommentResponse parent;
	private List<CommentResponse> children;
	private Long userId;
}
