package com.numble.backend.game.dto.response;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.numble.backend.game.domain.Game;
import com.numble.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetReviewsResponse {

	private Long id;

	private String content;

	private Double rate;

	private String user;

}
