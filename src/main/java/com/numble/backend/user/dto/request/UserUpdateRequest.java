package com.numble.backend.user.dto.request;

import javax.validation.constraints.NotNull;

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

	@NotNull(message = "시는 null일 수 없습니다")
	private String city;

	@NotNull(message = "동은 null일 수 없습니다")
	private String dong;

	@NotNull(message = "자기소개는 null일 수 없습니다")
	private String introduction;
}
