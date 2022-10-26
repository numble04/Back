package com.numble.backend.post.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.mapper.PostCreateMapper;
import com.numble.backend.post.domain.mapper.PostMapper;
import com.numble.backend.post.domain.PostRepository;

import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.post.dto.response.PostResponses;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;


	private final UserRepository userRepository;


	@Transactional
	public Long save(Long userId, PostCreateRequest postRequest){

		User user =userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Post post=PostCreateMapper.INSTANCE.ToEntity(postRequest,user);

		return postRepository.save(post).getId();
	}

	public PostResponses findAll() {

		final List<PostResponse> postResponses = postRepository.findAll()
			.stream()
			.map(PostMapper.INSTANCE::ToDto)
			.collect(Collectors.toList());

		return new PostResponses(postResponses);
	}

	public PostResponses findAllByUserId(Long userId) {

		User user =userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		final List<PostResponse> postResponses = postRepository.findAllByUser(user)
			.stream()
			.map(PostMapper.INSTANCE::ToDto)
			.collect(Collectors.toList());

		return new PostResponses(postResponses);
	}

	public PostResponse findById(Long postId) {
		Post post =postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());


		return PostMapper.INSTANCE.ToDto(post);
	}
}
