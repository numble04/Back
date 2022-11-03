package com.numble.backend.user.presentation;

import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.user.application.AuthService;
import com.numble.backend.user.dto.response.UserTokenResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@RequestMapping("/reissue")
	public ResponseEntity<ResponseDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
		ResponseDto responseDto = ResponseDto.of(authService.reissue(refreshToken));
		return ResponseEntity.ok(responseDto);
	}
}
