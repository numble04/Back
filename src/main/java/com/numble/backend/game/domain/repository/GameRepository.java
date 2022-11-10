package com.numble.backend.game.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.numble.backend.game.domain.Game;
import com.numble.backend.game.dto.response.GetGameResponse;

public interface GameRepository extends JpaRepository<Game,Long> {

	Slice<Game> findByOrderByTitleAsc(Pageable pageable);

	Slice<Game> findByOrderByTitleDesc(Pageable pageable);

	Slice<Game> findByOrderByLevelAsc(Pageable pageable);

	Slice<Game> findByOrderByRateAsc(Pageable pageable);

	Slice<Game> findByTitleLikeOrderByTitleAsc(Pageable pageable,String title);

	Slice<Game> findByTitleLikeOrderByTitleDesc(Pageable pageable,String title);

	Slice<Game> findByTitleLikeOrderByLevelAsc(Pageable pageable,String title);

	Slice<Game> findByTitleLikeOrderByRateAsc(Pageable pageable,String title);
}
