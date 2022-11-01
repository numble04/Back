package com.numble.backend.user.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {
	@NotNull
	private String email;
	@NotNull
	private String password;
}
