package com.numble.backend.game.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.user.domain.User;

public interface GameReviewRepository extends JpaRepository<GameReview,Long>,GameReviewRepositoryCustom {
	Boolean existsByUserAndGame(User user, Game game);
}
