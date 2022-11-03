package com.numble.backend.user.domain;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.dto.request.UserUpdateRequest;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_user_email", columnNames = {"email"})})
public class User extends BaseEntity {

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false, length = 20)
	private String phone;
	private String img;
	private String region;




	public void updateUser(UserUpdateRequest userUpdateRequest) {

		this.img = userUpdateRequest.getImg();
		this.region = userUpdateRequest.getRegion();

	}
}
