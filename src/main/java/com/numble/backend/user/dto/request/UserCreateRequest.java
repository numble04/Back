package com.numble.backend.user.dto.request;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateRequest {
	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String phone;

	@NotNull
	private String nickname;

}
