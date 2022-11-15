package com.numble.backend.meeting.domain.repository;

import static com.numble.backend.meeting.domain.QMeeting.meeting;
import static com.numble.backend.meeting.domain.QMeetingLike.meetingLike;
import static com.numble.backend.meeting.domain.QMeetingUser.meetingUser;
import static com.numble.backend.post.domain.QPost.post;
import static com.numble.backend.post.domain.QPostLike.postLike;
import static com.numble.backend.user.domain.QUser.user;
import static com.querydsl.core.types.ExpressionUtils.count;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import com.numble.backend.meeting.domain.QMeetingLike;
import com.numble.backend.meeting.domain.QMeetingUser;
import com.numble.backend.meeting.dto.response.MeetingDetailResponse;
import com.numble.backend.meeting.dto.response.MeetingResponse;
import com.numble.backend.meeting.dto.response.MeetingUserResponse;
import com.numble.backend.meeting.dto.response.QMeetingDetailResponse;
import com.numble.backend.meeting.dto.response.QMeetingResponse;

import com.numble.backend.meeting.dto.response.QMeetingUserResponse;
import com.numble.backend.post.dto.response.PostDetailResponse;
import com.numble.backend.post.dto.response.QPostDetailResponse;
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
					.where(meetingUser.meeting.eq(meeting)
						.and(meetingUser.isApproved.eq(Boolean.TRUE))
						.and(meetingUser.isRejected.eq(Boolean.FALSE))),
				meeting.day,
				meeting.img,
				meeting.cafe,
				meeting.isFull
			))
			.from(meetingUser)
			.innerJoin(meetingUser.meeting, meeting)
			.where(meeting.cafe.city.eq(city)
				.and(meeting.cafe.dong.eq(dong))
				.and(dateBetween(startDate, endDate)))
			.groupBy(meeting)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.orderBy(meeting.isFull.asc(), meetingSort(pageable, latitude, longitude))
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	@Override
	public Optional<MeetingDetailResponse> findDetailById(Long id, Long userId, boolean isLeader) {

		Optional<MeetingDetailResponse> response = Optional.ofNullable(queryFactory
			.select(new QMeetingDetailResponse(
				meeting.id,
				meeting.title,
				meeting.content,
				meeting.kakaoUrl,
				meeting.img,
				meeting.time,
				meeting.cost,
				meeting.capacity,
				JPAExpressions
					.select(count(meetingUser))
					.from(meetingUser)
					.where(meetingUser.meeting.id.eq(id)
						.and(meetingUser.isApproved.eq(Boolean.TRUE))
						.and(meetingUser.isRejected.eq(Boolean.FALSE))),
				meeting.meetingLikes.size(),
				meeting.day,
				meeting.isFull,
				meeting.cafe,
				JPAExpressions
					.select()
					.from(meetingLike)
					.where(meetingLike.meeting.eq(meeting).and(user.id.eq(userId)))
					.exists(),
				JPAExpressions
					.select()
					.from(meetingUser)
					.where(meetingUser.meeting.eq(meeting).and(user.id.eq(userId)))
					.exists()
			))
			.from(meetingUser)
			.innerJoin(meetingUser.meeting, meeting).on(meeting.id.eq(id))
			.groupBy(meetingUser.meeting)
			.fetchOne());

		List<MeetingUserResponse> meetingUserResponses = queryFactory
			.select(new QMeetingUserResponse(
					meetingUser.user.id,
					meetingUser.user.nickname,
					meetingUser.user.img,
					meetingUser.user.region,
					meetingUser.isApproved,
					meetingUser.isLeader
				)
			)
			.from(meetingUser)
			.where(meetingUser.meeting.id.eq(id)
				.and(meetingUser.isRejected.eq(Boolean.FALSE))
				.and(eqApproved(isLeader)))
			.orderBy(meetingUser.isLeader.desc(), meetingUser.isApproved.desc())
			.fetch();

		response.get().setUsers(meetingUserResponses);
		response.get().setIsLeader(isLeader);

		return response;
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

	private BooleanExpression eqApproved(boolean isLeader) {

		if (isLeader) {
			return null;
		}

		return meetingUser.isApproved.eq(Boolean.TRUE);
	}

}