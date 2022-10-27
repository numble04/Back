package com.numble.backend.user.domain;

import com.numble.backend.common.domain.BaseEntity;

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

	private String imgUrl;

	private String region;

	private String time;

	private String gameCate;

}
