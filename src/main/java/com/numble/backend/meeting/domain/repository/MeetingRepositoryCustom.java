package com.numble.backend.meeting.domain.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.meeting.dto.response.MeetingDetailResponse;
import com.numble.backend.meeting.dto.response.MeetingResponse;
import com.numble.backend.meeting.dto.response.MyMeetingResponse;
import com.numble.backend.post.dto.response.PostDetailResponse;

public interface MeetingRepositoryCustom {

	Slice<MeetingResponse> findAllByDong(String city, String dong, Double latitude, Double longitude,
		LocalDate startDate, LocalDate endDate, Pageable pageable);
	Slice<MyMeetingResponse> findAllByUserId(Long userId, Pageable pageable);
	Optional<MeetingDetailResponse> findDetailById(Long id, Long userId,boolean isLeader);
}
