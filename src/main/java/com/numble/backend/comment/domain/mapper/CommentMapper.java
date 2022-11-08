package com.numble.backend.comment.domain.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.comment.dto.request.CommentCreateRequest;
import com.numble.backend.comment.dto.response.CommentResponse;
import com.numble.backend.post.domain.Post;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

	@Mapping(expression = "java(dto.getContent())", target="content")
	@Mapping(expression = "java(post)", target="post")
	@Mapping(expression = "java(user)", target="user")
	Comment toEntity(CommentCreateRequest dto, Post post, User user);

	// @Mapping(expression = "java(dto.getContent())", target="content")
	// @Mapping(expression = "java(comment)", target="parent")
	// @Mapping(expression = "java(user)", target="user")
	// Comment toEntity(CommentRequest dto, Comment comment, User user);

	@Mapping(expression = "java(comment.getPost().getId())", target="postId")
	CommentResponse toDto(Comment comment);

}
