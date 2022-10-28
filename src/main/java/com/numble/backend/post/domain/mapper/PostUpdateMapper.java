package com.numble.backend.post.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.post.domain.Post;

import com.numble.backend.post.dto.request.PostUpdateRequest;

@Mapper(componentModel = "spring")
public interface PostUpdateMapper {

	PostUpdateMapper INSTANCE = Mappers.getMapper(PostUpdateMapper.class);

	Post ToEntity(PostUpdateRequest dto);


}
