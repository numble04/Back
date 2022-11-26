package com.numble.backend.game.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.game.domain.Game;

public interface GameRepository extends JpaRepository<Game,Long>,GameRepositoryCustom {
}
