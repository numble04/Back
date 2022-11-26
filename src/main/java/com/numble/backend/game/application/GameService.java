package com.numble.backend.game.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.mapper.GameDetailMapper;
import com.numble.backend.game.domain.repository.GameRepository;
import com.numble.backend.game.dto.response.GameDetailResponse;
import com.numble.backend.game.dto.response.GameResponse;
import com.numble.backend.game.exception.GameNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
	private final GameRepository gameRepository;

	public Slice<GameResponse> findAll(Pageable pageable) {

		Slice<GameResponse> responses = gameRepository.findAllByNone(pageable);

		return responses;
	}

	public Slice<GameResponse> findAllBySearch(Pageable pageable, String title) {
		Slice<GameResponse> responses = gameRepository.findAllBySearch(pageable,title);

		return responses;
	}

	public GameDetailResponse findById(Long id) {
		GameDetailResponse responses = GameDetailMapper.INSTANCE.toDto(
			gameRepository.findById(id)
			.orElseThrow(() ->  new GameNotFoundException()));

		return responses;
	}

}
