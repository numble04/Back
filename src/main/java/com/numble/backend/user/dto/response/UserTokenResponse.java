package com.numble.backend.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenResponse {
	private UserResponse userResponse;
	private String accessToken;
	private String refreshToken;
}
