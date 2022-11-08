package com.numble.backend.comment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.comment.domain.CommentRepository;
import com.numble.backend.comment.domain.mapper.CommentMapper;
import com.numble.backend.comment.dto.request.CommentCreateRequest;
import com.numble.backend.comment.dto.response.CommentResponse;
import com.numble.backend.comment.exception.CommentNotFoundException;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public List<CommentResponse> findAllByUserId(CustomUserDetails customUserDetails) {
		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		List<CommentResponse> commentResponses = commentRepository.findAllByUser(user)
			.stream()
			.map(CommentMapper.INSTANCE::toDto)
			.collect(Collectors.toList());

		return commentResponses;
	}

	@Transactional
	public Long saveByPostId(Long postId, CommentCreateRequest commentCreateRequest, CustomUserDetails customUserDetails) {
		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		Comment comment = CommentMapper.INSTANCE.toEntity(commentCreateRequest, post, user);

		return commentRepository.save(comment).getId();
	}

	@Transactional
	public Long saveChildById(Long id, CommentCreateRequest commentCreateRequest, CustomUserDetails customUserDetails) {
		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Comment parent = commentRepository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException());

		Post post = parent.getPost();

		Comment comment = CommentCreateRequest.toEntity(commentCreateRequest, post, parent, user);

		return commentRepository.save(comment).getId();
	}

}
