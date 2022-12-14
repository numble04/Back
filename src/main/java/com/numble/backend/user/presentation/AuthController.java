package com.numble.backend.user.presentation;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.user.application.AuthService;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
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

	@GetMapping("/email")
	public ResponseEntity<ResponseDto> findEmailExists(@RequestParam("email") @NotBlank(message = "???????????? ??? ?????? ??? ????????????")
	@Email(message = "????????? ????????? ?????? ????????????") final String email) {
		authService.findEmailExists(email);
		ResponseDto responseDto = ResponseDto.of("????????? ?????? ???????????????.");

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/nickname")
	public ResponseEntity<ResponseDto> findNicknameExists(@RequestParam("nickname") @NotBlank(message = "???????????? ??? ?????? ??? ????????????")
	@Size(min=3, max=15, message = "???????????? 3??? ?????? 15??? ???????????? ?????????") final String nickname) {
		authService.findNicknameExists(nickname);
		ResponseDto responseDto = ResponseDto.of("????????? ?????? ???????????????.");

		return ResponseEntity.ok(responseDto);
	}

}
