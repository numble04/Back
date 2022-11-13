package com.numble.backend.cafe.domain;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

	Slice<Cafe> findByNameContainingOrDongContaining(String name,String dong,Pageable pageable);

}
