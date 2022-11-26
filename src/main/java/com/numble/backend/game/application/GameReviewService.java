package com.numble.backend.game.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.mapper.GameReviewCreateMapper;
import com.numble.backend.game.domain.repository.GameRepository;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.domain.repository.GameReviewRepository;
import com.numble.backend.game.dto.request.GameReviewCreateRequest;
import com.numble.backend.game.dto.request.GameReviewUpdateRequest;
import com.numble.backend.game.dto.response.ReviewResponse;
import com.numble.backend.game.exception.GameNotFoundException;
import com.numble.backend.game.exception.GameReviewExistException;
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
	public Long save(Long gameId, Long userId, GameReviewCreateRequest gameReviewCreateRequest) {
		User user = userRepository.findById(userId)
			.orElseThrow(() ->  new UserNotFoundException());
		Game game = gameRepository.findById(gameId)
			.orElseThrow(() ->  new GameNotFoundException());

		validateReview(user,game);

		GameReview gameReview  = GameReviewCreateMapper.INSTANCE
			.toEntity(gameReviewCreateRequest,user,game);

		return gameReviewRepository.save(gameReview).getId();
	}

	private void validateReview(User user, Game game) {
		if (gameReviewRepository.existsByUserAndGame(user, game)) {
			throw new GameReviewExistException();
		}
	}

	public Slice<ReviewResponse> findReviewsByGameId(Long userId, Long gameId, Pageable pageable) {
		Slice<ReviewResponse> responses = gameReviewRepository.findAllByGameId(userId,gameId,pageable);

		return responses;
	}

	@Transactional
	public void updateById(Long userId, Long reviewId, GameReviewUpdateRequest gameReviewUpdateRequest) {
		GameReview gameReview = gameReviewRepository.findById(reviewId)
			.orElseThrow(() -> new GameReviewsNotFoundException());

		gameReview.updateGameReview(gameReviewUpdateRequest,userId);
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
