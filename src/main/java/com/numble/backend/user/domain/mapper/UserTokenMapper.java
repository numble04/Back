package com.numble.backend.user.domain.mapper;

import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserTokenMapper {
	UserTokenMapper INSTANCE = Mappers.getMapper(UserTokenMapper.class);

	@Mapping(source = "userResponse", target="userResponse")
	UserTokenResponse toDto(Token token, UserResponse userResponse);
}
