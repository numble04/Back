package com.numble.backend.post.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.numble.backend.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long>,PostRepositoryCustom {
	@EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
	Optional<Post> findById(Long id);

	@EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
	List<Post> findAllById(Long id);

	@EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
	List<Post> findAllByUser(User user);

	@EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
	List<Post> findAllByType(PostType type);

}
