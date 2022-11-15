package com.numble.backend.game.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.dto.response.GetReviewsResponse;

public interface GameReviewRepositoryCustom {
	Slice<GetReviewsResponse> findAllByGameId(Long gameId, Pageable pageable);
}
