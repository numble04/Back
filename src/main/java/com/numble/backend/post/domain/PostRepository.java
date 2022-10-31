package com.numble.backend.post.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.numble.backend.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findById(Long id);

	 List<Post> findAllById(Long id);
	 List<Post> findAllByUser(User user);

	 List<Post> findAllByType(PostType type);

}
