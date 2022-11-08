package com.numble.backend.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequest {
	private String nickname;
	private String name;
	private String phone;
	private String img;
	private String region;
}
