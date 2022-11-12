package com.numble.backend.meeting.domain.repository;

import static com.numble.backend.meeting.domain.QMeeting.meeting;
import static com.numble.backend.meeting.domain.QMeetingUser.meetingUser;
import static com.querydsl.core.types.ExpressionUtils.count;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;


import com.numble.backend.meeting.dto.response.MeetingResponse;
import com.numble.backend.meeting.dto.response.QMeetingResponse;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Slice<MeetingResponse> findAllByDong(String city, String dong, Double latitude, Double longitude,
		LocalDate startDate, LocalDate endDate,
		Pageable pageable) {

		List<MeetingResponse> content = queryFactory
			.select(new QMeetingResponse(
				meeting.id,
				meeting.title,
				meeting.capacity,
				JPAExpressions
					.select(count(meetingUser))
					.from(meetingUser)
					.where(meetingUser.meeting.eq(meeting).and(meetingUser.isApproved.eq(Boolean.TRUE))),
				meeting.day,
				meeting.img,
				meeting.cafe,
				meeting.isFull
			))
			.from(meetingUser)
			.innerJoin(meetingUser.meeting, meeting)
			.where(meeting.cafe.city.eq(city)
				.and(meeting.cafe.dong.eq(dong))
				.and(meeting.day.after(LocalDateTime.now().minusDays(1)))
				.and(dateBetween(startDate, endDate)))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.orderBy(meeting.isFull.desc(), meetingSort(pageable, latitude, longitude))
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	private OrderSpecifier<?> meetingSort(Pageable page, Double latitude, Double longitude) {

		if (!page.getSort().isEmpty()) {
			for (Sort.Order order : page.getSort()) {

				switch (order.getProperty()) {
					case "createdAt":
						return new OrderSpecifier(Order.DESC, meeting.createdAt);
					case "popularity":
						return new OrderSpecifier(Order.DESC, meeting.meetingLikes.size());
					case "day":
						return new OrderSpecifier(Order.ASC, meeting.day);
					case "distance":
						return new OrderSpecifier(Order.ASC,
							Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
								Expressions.stringTemplate("POINT({0}, {1})", longitude, latitude),
								meeting.cafe.point
							));
				}
			}
		}
		return null;
	}

	private BooleanExpression dateBetween(LocalDate startDate, LocalDate endDate) {

		if (startDate == null || endDate == null) {
			return meeting.day.after(LocalDateTime.now().minusDays(1));
		}

		return meeting.day.between(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
	}

}
