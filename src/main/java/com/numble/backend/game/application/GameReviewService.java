package com.numble.backend.game.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.repository.GameRepository;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.domain.repository.GameReviewRepository;
import com.numble.backend.game.domain.mapper.GetReviewsResponseMapper;
import com.numble.backend.game.domain.mapper.PostGameReviewRequestMapper;
import com.numble.backend.game.dto.request.PostGameReviewRequest;
import com.numble.backend.game.dto.request.UpdateGameReviewRequest;
import com.numble.backend.game.dto.response.GetReviewsResponse;
import com.numble.backend.game.exception.GameNotFoundException;
import com.numble.backend.game.exception.GameReviewsNotFoundException;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotAuthorException;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameReviewService {

	private final GameReviewRepository gameReviewRepository;

	private final UserRepository userRepository;

	private final GameRepository gameRepository;

	@Transactional
	public Long save(Long gameId, Long userId, PostGameReviewRequest postGameReviewRequest) {
		User user = userRepository.findById(userId)
			.orElseThrow(() ->  new UserNotFoundException());
		Game game = gameRepository.findById(gameId)
			.orElseThrow(() ->  new GameNotFoundException());

		GameReview gameReview  = PostGameReviewRequestMapper.INSTANCE
			.toEntity(postGameReviewRequest,user,game);

		return gameReviewRepository.save(gameReview).getId();
	}

	public Slice<GetReviewsResponse> findReviewsByGameId(Long id, Pageable pageable) {
		Slice<GetReviewsResponse> responses = gameReviewRepository.findAllByGameId(id,pageable)
				.map(GetReviewsResponseMapper.INSTANCE::toDto);

		return responses;
	}

	@Transactional
	public void updateById(Long userId, Long reviewId, UpdateGameReviewRequest updateGameReviewRequest) {
		GameReview gameReview = gameReviewRepository.findById(reviewId)
			.orElseThrow(() -> new GameReviewsNotFoundException());

		gameReview.updateGameReview(updateGameReviewRequest,userId);
	}

	@Transactional
	public void deleteById(Long userId, Long reviewId) {
		validateDelete(userId,reviewId);
		gameReviewRepository.deleteById(reviewId);
	}

	private void validateDelete(Long userId, Long reviewId) {
		if (userId != gameReviewRepository.findById(reviewId).get().getUser().getId()) {
			throw new UserNotAuthorException();
		}
	}

}
