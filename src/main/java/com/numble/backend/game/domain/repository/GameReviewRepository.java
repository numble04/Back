package com.numble.backend.game.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.domain.GameReview;
import com.numble.backend.user.domain.User;

public interface GameReviewRepository extends JpaRepository<GameReview,Long>,GameReviewRepositoryCustom {
	Boolean existsByUserAndGame(User user, Game game);
}
