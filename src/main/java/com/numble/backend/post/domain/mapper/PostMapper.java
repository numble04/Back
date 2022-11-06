package com.numble.backend.post.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostRequest;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);


	Post toEntity(PostRequest dto,User user);

	// @Mapping(expression = "java(null)", target="thumbnail")
	// PostResponse toDto(Post post, Long userId);
}
