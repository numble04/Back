package com.numble.backend.cafe.domain.repository;

import static com.numble.backend.cafe.domain.QCafe.cafe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.numble.backend.cafe.dto.response.CafeResponse;
import com.numble.backend.cafe.dto.response.QCafeResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public Slice<CafeResponse> findCafesContainingTitleAndDong(String keyword, Pageable pageable) {

		List<CafeResponse> content = new ArrayList<>();

		if (keyword!=""){
			content = queryFactory
				.select(new QCafeResponse(
					cafe.id,
					cafe.name
				))
				.from(cafe)
				.where(cafe.name.contains(keyword).or(cafe.dong.contains(keyword)))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize() + 1)
				.orderBy(cafe.id.asc())
				.fetch();
		}

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(content, pageable, hasNext);
	}
}
