package com.numble.backend.meeting.domain.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.meeting.dto.response.MeetingResponse;

public interface MeetingRepositoryCustom {

	Slice<MeetingResponse> findAllByDong(String city, String dong, Double latitude, Double longitude,
		LocalDate startDate, LocalDate endDate, Pageable pageable);
}
