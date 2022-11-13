package com.numble.backend.cafe.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.numble.backend.cafe.domain.Cafe;
import com.numble.backend.cafe.domain.CafeRepository;
import com.numble.backend.cafe.domain.mapper.CafeResponseMapper;
import com.numble.backend.cafe.dto.response.CafeResponse;
import com.numble.backend.cafe.exception.CafeNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	public Slice<CafeResponse> findCafe(String keyword, Pageable pageable) {
		Slice<CafeResponse> responses = cafeRepository
			.findByNameContainingOrDongContaining(keyword,keyword, pageable)
			.map(CafeResponseMapper.INSTANCE::toDto);

		return responses;
	}

}
