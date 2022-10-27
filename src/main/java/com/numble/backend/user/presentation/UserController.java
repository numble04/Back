package com.numble.backend.user.presentation;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.user.application.UserService;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.request.UserRequest;
import com.numble.backend.user.dto.request.UserUpdateRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<Long> findById(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws
		UserNotFoundException {

		return ResponseEntity.ok(customUserDetails.getId());
	}


	@PostMapping
	public ResponseEntity<Void> save(@RequestBody UserCreateRequest userCreateRequest) {
		Long id = userService.save(userCreateRequest);

		return ResponseEntity.created(URI.create("/api/users/" + id)).build();
	}


	@PostMapping("/login")
	public ResponseEntity<UserTokenResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		UserTokenResponse response = userService.login(userLoginRequest);

		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody UserUpdateRequest userUpdateRequest) {
		userService.updateUserInfoById(customUserDetails.getId(),userUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		userService.deleteById(accessToken,refreshToken,customUserDetails.getId());

		return ResponseEntity.noContent().build();
	}


	@GetMapping("/health")
	public ResponseEntity<String> health() {

		return ResponseEntity.ok("authorize 확인");
	}


	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		Long id = userService.logout(accessToken, refreshToken);

		return ResponseEntity.created(URI.create("/api/users/register/" + id)).build();
	}
}
