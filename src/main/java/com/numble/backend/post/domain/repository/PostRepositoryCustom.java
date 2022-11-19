package com.numble.backend.post.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.dto.response.MyPostResponse;
import com.numble.backend.post.dto.response.PostDetailResponse;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.user.domain.User;

public interface PostRepositoryCustom {
	Optional<PostDetailResponse> findOnePostById(Long postId, Long userId);

	Slice<PostResponse> findAllByType(PostType type, Long userId, Pageable pageable);

	Slice<PostResponse> findAllByTypeAndSearch(PostType type, String searchWord, Long userId, Pageable pageable);

	Slice<MyPostResponse> findAllByUser(Long userId, Pageable pageable);

	Slice<MyPostResponse> findAllByUserAndLike(Long userId, Pageable pageable);
}
