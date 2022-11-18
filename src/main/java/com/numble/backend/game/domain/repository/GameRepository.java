package com.numble.backend.game.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.numble.backend.game.domain.Game;

public interface GameRepository extends JpaRepository<Game,Long>,GameRepositoryCustom {
}
