package com.numble.backend.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
	private Long id;
	private String email;
	private String name;
	private String nickname;
	private String phone;
	private String img;
	private String region;
	private String city;
	private String dong;
	private String introduction;
}
