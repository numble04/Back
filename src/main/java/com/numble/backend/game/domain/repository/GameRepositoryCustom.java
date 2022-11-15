package com.numble.backend.game.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.game.dto.response.GetGameResponse;

public interface GameRepositoryCustom {

	Slice<GetGameResponse> findAllByNone(Pageable pageable);
	Slice<GetGameResponse> findAllBySearch(Pageable pageable, String title);

}
