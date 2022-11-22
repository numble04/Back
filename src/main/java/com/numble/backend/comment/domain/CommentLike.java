package com.numble.backend.comment.domain;

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
public class CommentLike extends BaseEntity {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "commentId")
	private Comment comment;

	public CommentLike(User user, Comment comment) {
		this.user = user;
		this.comment = comment;
	}
}
