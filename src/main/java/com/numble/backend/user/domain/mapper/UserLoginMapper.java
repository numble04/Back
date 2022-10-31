package com.numble.backend.user.domain.mapper;

import com.numble.backend.user.domain.Token;
import com.numble.backend.user.dto.response.UserTokenResponse;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserLoginMapper {
	UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

	UserTokenResponse toDto(Token t);
}
