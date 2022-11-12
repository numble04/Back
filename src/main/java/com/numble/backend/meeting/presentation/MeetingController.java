package com.numble.backend.meeting.presentation;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.meeting.application.MeetingService;
import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.dto.request.MeetingCreateRequest;
import com.numble.backend.post.application.PostService;
import com.numble.backend.post.dto.request.PostCreateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService meetingService;

	@PostMapping // 모임 생성
	public ResponseEntity<Void> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart(value = "meetingRequest") @Valid MeetingCreateRequest meetingCreateRequest,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile) {

		final Long id = meetingService.save(customUserDetails, meetingCreateRequest, multipartFile);

		return ResponseEntity.created(URI.create("/api/meetings/" + id)).build();
	}

	@GetMapping // 모임 조회
	public ResponseEntity<ResponseDto> findAllByDong(@PathParam("city") String city,
		@PathParam("dong") String dong,
		@PathParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		@PathParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
		@PathParam("latitude") Double latitude, @PathParam("longitude") Double longitude,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(
			meetingService.findAllByDong(city, dong, latitude, longitude, startDate, endDate, pageable));

		return ResponseEntity.ok(responseDto);
	}
}
