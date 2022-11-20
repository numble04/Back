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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.meeting.application.MeetingService;
import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.dto.request.MeetingCreateRequest;
import com.numble.backend.meeting.dto.request.MeetingUpdateRequest;
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

	@GetMapping("/{id}") // 모임 상세 조회
	public ResponseEntity<ResponseDto> findById(
		@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(meetingService.findById(id, customUserDetails));

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}") //모임 수정
	public ResponseEntity<ResponseDto> update(@PathVariable Long id,
		@RequestPart(value = "meetingRequest") @Valid MeetingUpdateRequest meetingUpdateRequest,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.update(id, meetingUpdateRequest, multipartFile, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}") //모임 삭제
	public ResponseEntity<ResponseDto> delete(@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.delete(id, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/my") //자신의 모임 조회
	public ResponseEntity<ResponseDto> findAllByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PageableDefault(size = 10) Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(meetingService.findAllByUserId(customUserDetails, pageable));

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/{id}/register") //모임 신청
	public ResponseEntity<ResponseDto> saveMeetingUser(
		@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		final Long meetingUserId = meetingService.saveMeetingUser(id, customUserDetails);

		return ResponseEntity.created(URI.create("/api/meetings/" + meetingUserId)).build();
	}

	@PutMapping("/{id}/approve/{userId}") //모임 신청 승인
	public ResponseEntity<ResponseDto> updateMeetingUserApprove(
		@PathVariable Long id, @PathVariable Long userId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.updateMeetingUserApprove(id, userId, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reject/{userId}") //모임 신청 거절
	public ResponseEntity<ResponseDto> updateMeetingUserReject(@PathVariable Long id, @PathVariable Long userId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.updateMeetingUserReject(id, userId, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/leave") //모임 나가기
	public ResponseEntity<ResponseDto> deleteMeetingUserByUserId(
		@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.deleteMeetingUserByUserId(id, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/ban/{userId}") //모임 강퇴
	public ResponseEntity<ResponseDto> updateMeetingUserBan(@PathVariable Long id, @PathVariable Long userId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.updateMeetingUserBan(id, userId, customUserDetails);

		return ResponseEntity.noContent().build();
	}
	@GetMapping("/like") //모임 좋아요 조회
	public ResponseEntity<ResponseDto> findAllByLike(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PageableDefault(size = 10) Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(meetingService.findAllByUserAndLike(customUserDetails, pageable));

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}/like") //모임 좋아요
	public ResponseEntity<ResponseDto> updateMeetingLike(@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		meetingService.updateMeetingLike(id, customUserDetails);

		return ResponseEntity.noContent().build();
	}

}
