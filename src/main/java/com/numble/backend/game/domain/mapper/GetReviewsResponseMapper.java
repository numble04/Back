package com.numble.backend.game.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.dto.response.GetReviewsResponse;

@Mapper(componentModel = "spring")
public interface GetReviewsResponseMapper {

	GetReviewsResponseMapper INSTANCE = Mappers.getMapper(GetReviewsResponseMapper.class);

	@Mapping(expression = "java(gameReview.getId())", target = "id")
	@Mapping(expression = "java(gameReview.getContent())", target="content")
	@Mapping(expression = "java(gameReview.getRate())", target="rate")
	@Mapping(expression = "java(gameReview.getUser().getNickname())", target="user")
	GetReviewsResponse toDto(GameReview gameReview);
}
