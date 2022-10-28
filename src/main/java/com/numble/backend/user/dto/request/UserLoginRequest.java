package com.numble.backend.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {
	private String email;
	private String password;
}
