package com.numble.backend.user.presentation;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.user.application.UserService;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.request.UserUpdateRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Void> save(@RequestBody @Valid UserCreateRequest userCreateRequest) {
		Long id = userService.save(userCreateRequest);

		return ResponseEntity.created(URI.create("/api/users/signup" + id)).build();
	}


	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
		ResponseDto responseDto = ResponseDto.of(userService.login(userLoginRequest));

		return ResponseEntity.ok(responseDto);
	}

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
		MultipartFile multipartFile) {
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
