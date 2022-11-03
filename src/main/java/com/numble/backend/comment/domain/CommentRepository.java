package com.numble.backend.comment.domain;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Post;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	Slice<Comment> findAllByPost(Post post);

}
