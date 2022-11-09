package com.numble.backend.user.presentation;

import java.net.URI;

import javax.validation.Valid;

import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.user.application.AuthService;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.response.UserTokenResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Void> save(@RequestBody @Valid UserCreateRequest userCreateRequest) {
		Long id = authService.save(userCreateRequest);

		return ResponseEntity.created(URI.create("/api/auth/signup" + id)).build();
	}


	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
		ResponseDto responseDto = ResponseDto.of(authService.login(userLoginRequest));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/reissue")
	public ResponseEntity<ResponseDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
		ResponseDto responseDto = ResponseDto.of(authService.reissue(refreshToken));
		return ResponseEntity.ok(responseDto);
	}
}
