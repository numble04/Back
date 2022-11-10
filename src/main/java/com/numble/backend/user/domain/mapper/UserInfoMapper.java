package com.numble.backend.user.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.user.domain.User;
import com.numble.backend.user.dto.response.UserInfoResponse;
import com.numble.backend.user.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
	UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

	UserInfoResponse toDto(User user);
}
