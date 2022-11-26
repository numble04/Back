package com.numble.backend.user.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.user.domain.User;
import com.numble.backend.user.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toEntity(UserResponse dto);

	UserResponse toDto(User user);
}
