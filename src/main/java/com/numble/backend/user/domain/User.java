package com.numble.backend.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.dto.request.UserUpdateRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "uk_user_email", columnNames = {"email"}),
	@UniqueConstraint(name = "uk_user_nickname", columnNames = {"nickname"})
})
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

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String dong;

	@Column(columnDefinition = "TEXT")
	private String introduction;




	public void updateUser(UserUpdateRequest userUpdateRequest) {

		if (userUpdateRequest.getName() != "") {
			this.name = userUpdateRequest.getName();
		}
		if (userUpdateRequest.getRegion() != ""
		&& userUpdateRequest.getCity() != ""
		&& userUpdateRequest.getDong() != "") {
			this.region = userUpdateRequest.getRegion();
			this.city = userUpdateRequest.getCity();
			this.dong = userUpdateRequest.getDong();
		}
		if (userUpdateRequest.getPhone() != "") {
			this.phone = userUpdateRequest.getPhone();
		}
		if (userUpdateRequest.getNickname() != "") {
			this.nickname = userUpdateRequest.getNickname();
		}
		if (userUpdateRequest.getIntroduction() != "") {
			this.introduction = userUpdateRequest.getIntroduction();
		}

	}

	public void updateImg(String img) {
		this.img = img;
	}

	public void deleteImg() {this.img = null;}
}
