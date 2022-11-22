package com.numble.backend.comment.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.user.domain.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByUser(User user);

}
