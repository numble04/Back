package com.numble.backend.post.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long>,PostRepositoryCustom {

	Slice<Post> findAllByUser(User user, Pageable pageable);

}
