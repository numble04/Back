package com.numble.backend.meeting.presentation;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.config.security.CustomUserDetails;
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
}
