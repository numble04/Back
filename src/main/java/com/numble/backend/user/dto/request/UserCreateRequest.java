package com.numble.backend.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateRequest {
	@NotBlank(message = "이메일은 빈 칸일 수 없습니다")
	@Email(message = "이메일 형식이 맞지 않습니다")
	private String email;

	@NotBlank(message = "비밀번호는 빈 칸일 수 없습니다")
	@Size(min=8,max=32,message = "비밀번호는 8자 이상 32자 이하로 작성해야 합니다")
	private String password;

	@NotBlank(message = "이름은 빈 칸일 수 없습니다")
	private String name;

	@NotBlank(message = "전화번호는 빈 칸일 수 없습니다")
	private String phone;

	@NotBlank(message = "닉네임은 빈 칸일 수 없습니다")
	@Size(min=3, max=15, message = "닉네임은 3자 이상 15자 이하여야 합니다")
	private String nickname;

	@NotBlank(message = "지역은 빈 칸일 수 없습니다")
	private String region;

	@NotBlank(message = "거주 시를 선택하십시오")
	private String city;

	@NotBlank(message = "거주 동을 선택하십시오")
	private String dong;

}
