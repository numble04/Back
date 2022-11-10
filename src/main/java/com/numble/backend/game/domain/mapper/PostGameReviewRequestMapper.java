package com.numble.backend.game.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.dto.request.PostGameReviewRequest;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring")
public interface PostGameReviewRequestMapper {

	PostGameReviewRequestMapper INSTANCE = Mappers.getMapper(PostGameReviewRequestMapper.class);

	@Mapping(source="postGameReviewRequest.rate",target="rate")
	GameReview toEntity(PostGameReviewRequest postGameReviewRequest, User user, Game game);
}
