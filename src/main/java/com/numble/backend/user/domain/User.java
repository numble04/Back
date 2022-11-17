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

	public void deleteImg() {this.img = "";}
}
