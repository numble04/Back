package com.numble.backend.comment.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
}
