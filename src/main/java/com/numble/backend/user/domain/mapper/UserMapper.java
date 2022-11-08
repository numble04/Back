package com.numble.backend.user.domain.mapper;

import com.numble.backend.user.domain.User;
import com.numble.backend.user.dto.response.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toEntity(UserResponse dto);

	UserResponse toDto(User user);
}
