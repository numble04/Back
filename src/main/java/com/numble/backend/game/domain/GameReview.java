package com.numble.backend.game.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.common.exception.business.InvalidFieldException;
import com.numble.backend.game.dto.request.GameReviewUpdateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.exception.UserNotAuthorException;

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
public class GameReview extends BaseEntity {

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Double rate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "gameId")
	private Game game;

	public void validateMemberIsAuthor(final Long userId) {
		final boolean isNotAuthor = !this.user.getId().equals(userId);
		if (isNotAuthor) {
			throw new UserNotAuthorException();
		}
	}

	public void updateGameReview(final GameReviewUpdateRequest gameReviewUpdateRequest, final Long userId) {
		validateMemberIsAuthor(userId);
		this.content = gameReviewUpdateRequest.getContent();
		this.rate = gameReviewUpdateRequest.getRate();
	}

}
