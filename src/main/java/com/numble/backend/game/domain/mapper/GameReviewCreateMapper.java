package com.numble.backend.game.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.dto.request.GameReviewCreateRequest;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring")
public interface GameReviewCreateMapper {

	GameReviewCreateMapper INSTANCE = Mappers.getMapper(GameReviewCreateMapper.class);

	@Mapping(source="gameReviewCreateRequest.rate",target="rate")
	GameReview toEntity(GameReviewCreateRequest gameReviewCreateRequest, User user, Game game);
}
