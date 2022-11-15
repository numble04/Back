package com.numble.backend.game.domain.repository;

import static com.numble.backend.game.domain.QGame.game;
import static com.numble.backend.game.domain.QGameReview.gameReview;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import com.numble.backend.game.domain.GameReview;
import com.numble.backend.game.dto.response.GetReviewsResponse;
import com.numble.backend.game.dto.response.QGetReviewsResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameReviewRepositoryImpl implements GameReviewRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public Slice<GetReviewsResponse> findAllByGameId(Long gameId, Pageable pageable) {
		List<GetReviewsResponse> content = queryFactory
			.select(new QGetReviewsResponse(
				gameReview.id,
				gameReview.content,
				gameReview.rate,
				gameReview.user.nickname,
				gameReview.createdAt,
				gameReview.updatedAt
			))
			.from(gameReview)
			.where(gameReview.game.id.eq(gameId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize()+1)
			.orderBy(gameReviewSort(pageable))
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}


		return new SliceImpl<>(content, pageable, hasNext);
	}

	private OrderSpecifier<?> gameReviewSort(Pageable page) {
		//서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
		if (!page.getSort().isEmpty()) {
			//정렬값이 들어 있으면 for 사용하여 값을 가져온다
			for (Sort.Order order : page.getSort()) {
				// 서비스에서 넣어준 DESC or ASC 를 가져온다.
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				// 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
				switch (order.getProperty()){
					case "rate":
						return new OrderSpecifier(direction, gameReview.rate);
					case "level":
						return new OrderSpecifier(direction, gameReview.createdAt);
				}
			}
		}
		return null;
	}
}
