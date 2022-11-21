package com.numble.backend.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
