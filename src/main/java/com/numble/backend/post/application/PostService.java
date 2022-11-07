package com.numble.backend.post.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.utils.S3Utils;
import com.numble.backend.post.domain.Image;
import com.numble.backend.post.domain.repository.ImageRepository;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.domain.mapper.PostCreateMapper;
import com.numble.backend.post.domain.mapper.PostMapper;
import com.numble.backend.post.domain.repository.PostRepository;

import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;
import com.numble.backend.post.dto.response.PostOneResponse;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.post.exception.FileCountExceedException;
import com.numble.backend.post.exception.FileUploadFailedException;
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

	private final ImageRepository imageRepository;
	private final AmazonS3Client amazonS3Client;
	private final UserRepository userRepository;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Transactional
	public Long save(CustomUserDetails customUserDetails, PostCreateRequest postRequest
		, List<MultipartFile> multipartFiles) {

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Post post = PostCreateMapper.INSTANCE.toEntity(postRequest, user);

		uploadFiles(multipartFiles, post);

		return postRepository.save(post).getId();
	}

	public Slice<PostResponse> findAllByType(PostType type, Pageable pageable, CustomUserDetails customUserDetails) {

		Slice<PostResponse> postResponses = postRepository.findAllByType(type, customUserDetails.getId(), pageable);

		return postResponses;
	}

	public List<PostResponse> findAllByUserId(CustomUserDetails customUserDetails) {

		// User user = userRepository.findById(customUserDetails.getId())
		// 	.orElseThrow(() -> new UserNotFoundException());
		//
		// final List<PostResponse> postResponses = postRepository.findAllByUser(user)
		// 	.stream()
		// 	.map((post) -> PostMapper.INSTANCE.toDto(post, customUserDetails.getId()))
		// 	.collect(Collectors.toList());
		//
		// return postResponses;
		return null;
	}

	@Transactional
	public PostOneResponse findById(Long postId, CustomUserDetails customUserDetails) {
		PostOneResponse postOneResponse = postRepository.findOnePostById(postId, customUserDetails.getId())
			.orElseThrow(() -> new PostNotFoundException());

		postOneResponse.setImages(
			imageRepository.findByPostId(postId).stream().map(i -> i.getUrl()).collect(Collectors.toList()));
		return postOneResponse;
	}

	@Transactional
	public void uploadFiles(List<MultipartFile> multipartFiles, Post post) {
		if (multipartFiles == null) {
			return;
		}

		List<String> fileNames = S3Utils.uploadMultiFilesS3(amazonS3Client, bucketName, multipartFiles, 5);

		for (String fileName : fileNames) {
			Image image = Image.builder()
				.url(amazonS3Client.getUrl(bucketName, fileName).toString())
				.post(post)
				.build();

			imageRepository.save(image);
		}
	}

	@Transactional
	public void updateById(CustomUserDetails customUserDetails, Long postId, PostUpdateRequest postUpdateRequest) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.updateContent(postUpdateRequest.getContent(), customUserDetails.getId());
	}

	@Transactional
	public void deleteById(CustomUserDetails customUserDetails, Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.validateMemberIsAuthor(customUserDetails.getId());

		postRepository.deleteById(postId);
	}

	@Transactional
	public void updateLikeById(CustomUserDetails customUserDetails, Long postId, PostUpdateRequest postUpdateRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

	}

}
