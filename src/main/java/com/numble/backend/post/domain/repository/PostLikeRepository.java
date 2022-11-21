package com.numble.backend.post.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostLike;
import com.numble.backend.user.domain.User;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	Optional<PostLike> findByPostAndUser(Post post, User user);
}
