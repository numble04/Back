package com.numble.backend.cafe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.cafe.domain.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long>, CafeRepositoryCustom {
}
