package com.numble.backend.meeting.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numble.backend.cafe.domain.Cafe;
import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.dto.request.MeetingCreateRequest;
import com.numble.backend.user.domain.User;

@Mapper(componentModel = "spring")
public interface MeetingCreateMapper {

	MeetingCreateMapper INSTANCE = Mappers.getMapper(MeetingCreateMapper.class);

	Meeting toEntity(MeetingCreateRequest dto, String img, Cafe cafe);
}
