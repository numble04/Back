package com.numble.backend.user.domain.mapper;

import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserMapper;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserCreateMapper {
	UserCreateMapper INSTANCE = Mappers.getMapper(UserCreateMapper.class);

	@Mapping(source="password",target="password")
	User ToEntity(UserCreateRequest dto, String password);

}
