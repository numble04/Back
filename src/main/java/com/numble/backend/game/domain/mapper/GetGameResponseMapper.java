package com.numble.backend.game.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.dto.response.GetGameResponse;

@Mapper(componentModel = "spring")
public interface GetGameResponseMapper {

	GetGameResponseMapper INSTANCE = Mappers.getMapper(GetGameResponseMapper.class);

	GetGameResponse toDto(Game game);
}
