package com.numble.backend.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@Getter
public class UserResponse {
	private String userId;
	private String name;
	private String password;
}
