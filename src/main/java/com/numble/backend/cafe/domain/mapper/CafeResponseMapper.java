package com.numble.backend.cafe.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.cafe.domain.Cafe;
import com.numble.backend.cafe.dto.response.CafeResponse;

@Mapper(componentModel = "spring")
public interface CafeResponseMapper {

	CafeResponseMapper INSTANCE = Mappers.getMapper(CafeResponseMapper.class);

	CafeResponse toDto(Cafe cafe);

}
