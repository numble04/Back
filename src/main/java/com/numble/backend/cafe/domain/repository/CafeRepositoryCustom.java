package com.numble.backend.cafe.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.cafe.dto.response.CafeResponse;

public interface CafeRepositoryCustom {
	Slice<CafeResponse> findCafesContainingTitleAndDong(String keyword, Pageable pageable);
}
