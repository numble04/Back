package com.numble.backend.game.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.GameRepository;
import com.numble.backend.game.domain.mapper.GetGameResponseMapper;
import com.numble.backend.game.dto.response.GetGameResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
	private final GameRepository gameRepository;

	public Page<GetGameResponse> findAll(Pageable pageable) {
		Page<GetGameResponse> responses = gameRepository.findAll(pageable)
			.map(GetGameResponseMapper.INSTANCE::toDto);

		return responses;
	}
}
