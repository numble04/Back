package com.numble.backend.user.domain;

import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "name") // 2
    User ToEntity(UserResponse dto);

//    @Mapping(source = "name", target = "name") // 2
    User ToEntity(UserCreateRequest dto);

    @Mapping(source = "name", target = "name")
    UserResponse ToDto(User user);
}
