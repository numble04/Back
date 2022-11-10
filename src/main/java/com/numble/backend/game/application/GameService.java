package com.numble.backend.game.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.repository.GameRepository;
import com.numble.backend.game.domain.mapper.GetDetailGameResponseMapper;
import com.numble.backend.game.domain.mapper.GetGameResponseMapper;
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

	public Slice<GetGameResponse> findAll(Pageable pageable, String type) {

		if (type.equals("오름차순")) {
			Slice<GetGameResponse> responses = gameRepository.findByOrderByTitleAsc(pageable)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("평점순")) {
			Slice<GetGameResponse> responses = gameRepository.findByOrderByRateAsc(pageable)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("난이도순")) {
			Slice<GetGameResponse> responses = gameRepository.findByOrderByLevelAsc(pageable)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("내림차순")) {
			Slice<GetGameResponse> responses = gameRepository.findByOrderByTitleDesc(pageable)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else {
			throw new GameTypeErrorException();
		}
	}

	public Slice<GetGameResponse> findAllBySearch(Pageable pageable, String title, String type) {
		if (type.equals("오름차순")) {
			Slice<GetGameResponse> responses = gameRepository.findByTitleLikeOrderByTitleAsc(pageable,title)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("평점순")) {
			Slice<GetGameResponse> responses = gameRepository.findByTitleLikeOrderByRateAsc(pageable,title)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("난이도순")) {
			Slice<GetGameResponse> responses = gameRepository.findByTitleLikeOrderByLevelAsc(pageable,title)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else if (type.equals("내림차순")) {
			Slice<GetGameResponse> responses = gameRepository.findByTitleLikeOrderByTitleDesc(pageable,title)
				.map(GetGameResponseMapper.INSTANCE::toDto);

			return responses;

		} else {
			throw new GameTypeErrorException();
		}
	}

	public GetDetailGameResponse findById(Long id) {
		GetDetailGameResponse responses = GetDetailGameResponseMapper.INSTANCE.toDto(
			gameRepository.findById(id)
			.orElseThrow(() ->  new GameNotFoundException()));

		return responses;
	}

}
