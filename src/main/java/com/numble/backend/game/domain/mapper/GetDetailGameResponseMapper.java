package com.numble.backend.game.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.dto.response.GetDetailGameResponse;

@Mapper(componentModel = "spring")
public interface GetDetailGameResponseMapper {
	GetDetailGameResponseMapper INSTANCE = Mappers.getMapper(GetDetailGameResponseMapper.class);

	GetDetailGameResponse toDto(Game game);
}
