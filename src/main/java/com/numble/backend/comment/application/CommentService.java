package com.numble.backend.comment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.comment.domain.CommentRepository;
import com.numble.backend.comment.domain.mapper.CommentMapper;
import com.numble.backend.comment.dto.request.CommentRequest;
import com.numble.backend.comment.dto.response.CommentResponse;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostRepository;
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


	public List<CommentResponse> findAllByPostId(Long postId) {
		Post post =postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		List<CommentResponse> commentResponses = commentRepository.findAllByPost(post)
			.stream()
			.map(CommentMapper.INSTANCE::toDto)
			.collect(Collectors.toList());

		return commentResponses;
	}

	@Transactional
	public Long saveByPostId(Long postId, CommentRequest commentRequest, CustomUserDetails customUserDetails) {

		User user =userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Post post =postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		Comment comment = CommentMapper.INSTANCE.toEntity(commentRequest,post,user);


		return commentRepository.save(comment).getId();
	}
}
