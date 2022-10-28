package com.numble.backend.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {
	private String accessToken;
	private String refreshToken;
}
