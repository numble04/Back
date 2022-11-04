package com.numble.backend.post.domain;

import java.util.Optional;

import com.numble.backend.post.dto.response.PostOneResponse;

public interface PostRepositoryCustom {
	Optional<PostOneResponse> findOnePostById(Long postId, Long userId);
}
