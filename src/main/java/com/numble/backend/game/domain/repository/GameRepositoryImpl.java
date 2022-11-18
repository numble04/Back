package com.numble.backend.game.domain.repository;

import static com.numble.backend.game.domain.QGame.game;
import static com.numble.backend.game.domain.QTheme.theme;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import com.numble.backend.game.domain.Theme;
import com.numble.backend.game.dto.response.GameResponse;
import com.numble.backend.game.dto.response.QGameResponse;
import com.numble.backend.game.dto.response.QThemeResponse;
import com.numble.backend.game.dto.response.ThemeResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public Slice<GameResponse> findAllByNone(Pageable pageable) {
		List<GameResponse> content = queryFactory
			.select(new QGameResponse(
				game.id,
				game.title,
				game.img,
				game.capacity,
				game.time,
				game.age,
				game.rate,
				game.level
			))
			.from(game)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize()+1)
			.orderBy(gameSort(pageable))
			.fetch();

		for (GameResponse GameResponse : content) {
			List<ThemeResponse> themes = queryFactory
				.select(new QThemeResponse(
					theme.name
				))
				.from(theme)
				.where(theme.game.id.eq(GameResponse.getId()))
				.fetch();

			GameResponse.setThemeList(themes);
		}

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}


		return new SliceImpl<>(content, pageable, hasNext);
	}

	@Override
	public Slice<GameResponse> findAllBySearch(Pageable pageable, String title) {
		List<GameResponse> content = queryFactory
			.select(new QGameResponse(
				game.id,
				game.title,
				game.img,
				game.capacity,
				game.time,
				game.age,
				game.rate,
				game.level
			))
			.from(game)
			.where(game.title.contains(title))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize()+1)
			.orderBy(gameSort(pageable))
			.fetch();

		for (GameResponse GameResponse : content) {
			List<ThemeResponse> themes = queryFactory
				.select(new QThemeResponse(
					theme.name
				))
				.from(theme)
				.where(theme.game.id.eq(GameResponse.getId()))
				.fetch();

			GameResponse.setThemeList(themes);
		}

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}


		return new SliceImpl<>(content, pageable, hasNext);
	}

	private OrderSpecifier<?> gameSort(Pageable page) {
		//서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
		if (!page.getSort().isEmpty()) {
			//정렬값이 들어 있으면 for 사용하여 값을 가져온다
			for (Sort.Order order : page.getSort()) {
				// 서비스에서 넣어준 DESC or ASC 를 가져온다.
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				// 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
				switch (order.getProperty()){
					case "title":
						return new OrderSpecifier(direction, game.title);
					case "rate":
						return new OrderSpecifier(direction, game.rate);
					case "level":
						return new OrderSpecifier(direction, game.level);
				}
			}
		}
		return null;
	}

}
