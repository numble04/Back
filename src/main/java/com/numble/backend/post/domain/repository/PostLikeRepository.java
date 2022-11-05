package com.numble.backend.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
}
