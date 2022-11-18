package com.numble.backend.game.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.dto.response.GameDetailResponse;

@Mapper(componentModel = "spring")
public interface GameDetailMapper {
	GameDetailMapper INSTANCE = Mappers.getMapper(GameDetailMapper.class);

	GameDetailResponse toDto(Game game);
}

