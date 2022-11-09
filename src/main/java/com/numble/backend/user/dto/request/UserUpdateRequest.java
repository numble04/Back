package com.numble.backend.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequest {

	@NotNull(message = "닉네임은 null일 수 없습니다")
	private String nickname;

	@NotNull(message = "이름은 null일 수 없습니다")
	private String name;

	@NotNull(message = "핸드폰 번호는 null일 수 없습니다")
	private String phone;

	@NotNull(message = "지역은 null일 수 없습니다")
	private String region;
}
