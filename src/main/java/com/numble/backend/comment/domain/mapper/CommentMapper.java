package com.numble.backend.comment.domain.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.comment.dto.request.CommentRequest;
import com.numble.backend.comment.dto.response.CommentResponse;
import com.numble.backend.post.domain.Post;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

	@Mapping(expression = "java(dto.getContent())", target="content")
	@Mapping(expression = "java(post)", target="post")
	@Mapping(expression = "java(user)", target="user")
	Comment toEntity(CommentRequest dto, Post post, User user);

	@Mapping(expression = "java(comment.getUser().getId())", target="userId")
	CommentResponse toDto(Comment comment);

}