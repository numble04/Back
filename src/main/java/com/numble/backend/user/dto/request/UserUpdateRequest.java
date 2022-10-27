package com.numble.backend.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequest {
	private String imgUrl;

	private String region;

	private String time;

	private String gameCate;
}
