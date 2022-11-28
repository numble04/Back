package com.numble.backend.cafe.application;

import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.cafe.domain.repository.CafeRepository;
import com.numble.backend.cafe.domain.mapper.CafeResponseMapper;
import com.numble.backend.cafe.dto.response.CafeResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	public Slice<CafeResponse> findByKeyword(String keyword, Pageable pageable) {
		boolean hasNext = false;
		Slice<CafeResponse> responses = new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
		if (keyword != "") {
			responses = cafeRepository
				.findByNameContainingOrDongContaining(keyword,keyword, pageable)
				.map(CafeResponseMapper.INSTANCE::toDto);
		}

		return responses;
	}

}
