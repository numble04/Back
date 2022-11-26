package com.numble.backend.user.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.user.application.UserService;
import com.numble.backend.user.dto.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<ResponseDto> findById(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		ResponseDto responseDto = ResponseDto.of(userService.findById(customUserDetails.getId()));

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
		userService.updateById(customUserDetails.getId(),userUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/profile")
	public ResponseEntity<Void> updateImg(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
		userService.updateImg(customUserDetails.getId(),multipartFile);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		userService.deleteById(accessToken,refreshToken,customUserDetails.getId());

		return ResponseEntity.noContent().build();
	}


	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		Long id = userService.logout(accessToken, refreshToken);

		return ResponseEntity.created(URI.create("/api/users/logout/" + id)).build();
	}
}
