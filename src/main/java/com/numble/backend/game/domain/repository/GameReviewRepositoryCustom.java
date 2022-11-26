package com.numble.backend.game.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.game.dto.response.ReviewResponse;

public interface GameReviewRepositoryCustom {
	Slice<ReviewResponse> findAllByGameId(Long userId, Long gameId, Pageable pageable);
}
