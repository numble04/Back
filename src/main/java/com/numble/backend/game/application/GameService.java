package com.numble.backend.game.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.repository.GameRepository;
import com.numble.backend.game.domain.mapper.GetDetailGameResponseMapper;
import com.numble.backend.game.dto.response.GetDetailGameResponse;
import com.numble.backend.game.dto.response.GetGameResponse;
import com.numble.backend.game.exception.GameNotFoundException;
import com.numble.backend.game.exception.GameTypeErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
	private final GameRepository gameRepository;

	public Slice<GetGameResponse> findAll(Pageable pageable) {

		Slice<GetGameResponse> responses = gameRepository.findAllByNone(pageable);

		return responses;
	}

	public Slice<GetGameResponse> findAllBySearch(Pageable pageable, String title) {
		Slice<GetGameResponse> responses = gameRepository.findAllBySearch(pageable,title);

		return responses;
	}

	public GetDetailGameResponse findById(Long id) {
		GetDetailGameResponse responses = GetDetailGameResponseMapper.INSTANCE.toDto(
			gameRepository.findById(id)
			.orElseThrow(() ->  new GameNotFoundException()));

		return responses;
	}

}
