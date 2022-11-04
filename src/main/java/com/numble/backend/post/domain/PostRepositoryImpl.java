package com.numble.backend.post.domain;

import static com.numble.backend.comment.domain.QComment.comment;
import static com.numble.backend.post.domain.QPost.post;
import static com.numble.backend.post.domain.QPostLike.postLike;
import static com.numble.backend.user.domain.QUser.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.numble.backend.comment.domain.QComment;
import com.numble.backend.comment.dto.response.CommentsChildrenResponse;
import com.numble.backend.comment.dto.response.PostOneCommentResponse;
import com.numble.backend.comment.dto.response.QCommentsChildrenResponse;
import com.numble.backend.comment.dto.response.QPostOneCommentResponse;
import com.numble.backend.post.dto.response.PostOneResponse;
import com.numble.backend.post.dto.response.QPostOneResponse;
import com.numble.backend.user.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<PostOneResponse> findOnePostById(Long postId, Long userId) {
		queryFactory.update(post)
			.set(post.viewCount, post.viewCount.add(1))
			.where(post.id.eq(postId))
			.execute();

		Optional<PostOneResponse> response = Optional.ofNullable(queryFactory
			.select(new QPostOneResponse(
				post.id,
				post.title,
				post.content,
				post.comments.size(),
				post.postLikes.size(),
				post.createdAt,
				post.updatedAt,
				post.viewCount,
				user.nickname,
				JPAExpressions
					.selectFrom(post)
					.where(user.id.eq(userId))
					.exists(),
				JPAExpressions
					.selectFrom(postLike)
					.where(postLike.post.eq(post).and(user.id.eq(userId)))
					.exists()))
			.from(post)
			.innerJoin(post.user, user)
			.where(post.id.eq(postId))
			.fetchOne());

		if (response.isEmpty()) {
			return Optional.empty();
		}

		List<PostOneCommentResponse> comments = queryFactory
			.select(new QPostOneCommentResponse(
				comment.parent.id,
				comment.id,
				comment.content,
				user.nickname,
				JPAExpressions
					.selectFrom(comment)
					.where(user.id.eq(userId))
					.exists(),
				comment.createdAt,
				comment.updatedAt))
			.from(comment)
			.innerJoin(comment.post, post)
			.innerJoin(comment.user, user)
			.where(post.id.eq(postId).and(comment.parent.id.isNull()))
			.orderBy(comment.id.asc())
			.fetch();

		List<CommentsChildrenResponse> childComments = queryFactory
			.select(new QCommentsChildrenResponse(
				comment.parent.id,
				comment.id,
				comment.content,
				user.nickname,
				JPAExpressions
					.selectFrom(comment)
					.where(user.id.eq(userId))
					.exists(),
				comment.createdAt,
				comment.updatedAt
			))
			.from(comment)
			.innerJoin(comment.post, post)
			.innerJoin(comment.user, user)
			.where(post.id.eq(postId).and(comment.parent.id.isNotNull()))
			.fetch();


		comments.stream()
			.forEach(parent -> {
				parent.setChildren(childComments.stream()
					.filter(child -> child.getParentId().equals(parent.getCommentId()))
					.collect(Collectors.toList()));
			});

		response.get().setComments(comments);

		return response;
	}
}
