package com.numble.backend.post.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.dto.response.PostOneResponse;
import com.numble.backend.post.dto.response.PostResponse;

public interface PostRepositoryCustom {
	Optional<PostOneResponse> findOnePostById(Long postId, Long userId);

	Slice<PostResponse> findAllByType (PostType type, Long userId,Pageable pageable);
	Slice<PostResponse> findAllByTypeAndSearch (PostType type, String searchWord, Long userId,Pageable pageable);
}
