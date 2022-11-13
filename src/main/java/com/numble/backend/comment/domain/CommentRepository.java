package com.numble.backend.comment.domain;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Post;
import com.numble.backend.user.domain.User;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	// @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
	// List<Comment> findAllByPostAndParentIsNull(Post post);
	List<Comment> findAllByUser(User user);

}
