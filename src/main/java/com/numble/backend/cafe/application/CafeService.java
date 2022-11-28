package com.numble.backend.cafe.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.cafe.domain.repository.CafeRepository;
import com.numble.backend.cafe.dto.response.CafeResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	public Slice<CafeResponse> findByKeyword(String keyword, Pageable pageable) {
		Slice<CafeResponse> responses = cafeRepository.findCafesContainingTitleAndDong(keyword, pageable);

		return responses;
	}

}
