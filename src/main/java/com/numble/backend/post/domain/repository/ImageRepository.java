package com.numble.backend.post.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.post.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findByPostId(Long postId);
}
