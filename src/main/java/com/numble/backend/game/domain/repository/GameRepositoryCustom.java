package com.numble.backend.game.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.game.dto.response.GameResponse;

public interface GameRepositoryCustom {

	Slice<GameResponse> findAllByNone(Pageable pageable);
	Slice<GameResponse> findAllBySearch(Pageable pageable, String title);

}
