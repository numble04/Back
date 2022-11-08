package com.numble.backend.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {

	@NotBlank(message = "이메일은 빈 칸일 수 없습니다")
	@Email(message = "이메일 형식이 맞지 않습니다")
	private String email;

	@NotBlank(message = "비밀번호는 빈 칸일 수 없습니다")
	private String password;

}
