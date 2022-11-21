package com.numble.backend.post.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostLike extends BaseEntity {

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId")
	private Post post;

	public PostLike(User user, Post post) {
		this.user = user;
		this.post = post;
	}
}
