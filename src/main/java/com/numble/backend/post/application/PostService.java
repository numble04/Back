package com.numble.backend.post.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.utils.S3Utils;
import com.numble.backend.post.domain.Image;
import com.numble.backend.post.domain.PostLike;
import com.numble.backend.post.domain.repository.ImageRepository;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.domain.mapper.PostCreateMapper;
import com.numble.backend.post.domain.repository.PostLikeRepository;
import com.numble.backend.post.domain.repository.PostRepository;

import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;
import com.numble.backend.post.dto.response.MyPostResponse;
import com.numble.backend.post.dto.response.PostDetailResponse;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostLikeRepository postLikeRepository;
	private final ImageRepository imageRepository;
	private final AmazonS3Client amazonS3Client;

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

	public Slice<PostResponse> findAllBySearch(PostType type, String searchWord, Pageable pageable,
		CustomUserDetails customUserDetails) {

		Slice<PostResponse> postResponses = postRepository.findAllByTypeAndSearch(type, searchWord,
			customUserDetails.getId(), pageable);

		return postResponses;
	}

	@Transactional
	public PostDetailResponse findById(Long postId, CustomUserDetails customUserDetails) {
		PostDetailResponse postDetailResponse = postRepository.findOnePostById(postId, customUserDetails.getId())
			.orElseThrow(() -> new PostNotFoundException());

		postDetailResponse.setImages(
			imageRepository.findByPostId(postId).stream().map(i -> i.getUrl()).collect(Collectors.toList()));
		return postDetailResponse;
	}

	public Slice<MyPostResponse> findAllByUserId(CustomUserDetails customUserDetails, Pageable pageable) {

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		final List<MyPostResponse> postResponses = postRepository.findAllByUser(user, pageable)
			.stream()
			.map(MyPostResponse::toDto)
			.collect(Collectors.toList());

		return new SliceImpl<>(postResponses,pageable,checkHasNext(postResponses, pageable));
	}

	private boolean checkHasNext(List<MyPostResponse> postResponses, Pageable pageable) {

		if (postResponses.size() > pageable.getPageSize()) {
			return true;
		}
		return false;
	}

	@Transactional
	public void updateById(CustomUserDetails customUserDetails, Long postId, PostUpdateRequest postUpdateRequest
		, List<MultipartFile> multipartFiles) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		List<Image> images = post.getImages();
		imageRepository.deleteInBatch(images);

		uploadFiles(multipartFiles, post);

		post.updatePost(postUpdateRequest, customUserDetails.getId());
	}

	@Transactional
	public void deleteById(CustomUserDetails customUserDetails, Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.validateMemberIsAuthor(customUserDetails.getId());

		postRepository.deleteById(postId);
	}

	@Transactional
	public void updateLikeById(CustomUserDetails customUserDetails, Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId,
			customUserDetails.getId());

		if (postLike.isPresent()) {
			postLikeRepository.delete(postLike.get());
		} else {
			PostLike postLike1 = new PostLike(user, post);
			postLikeRepository.save(postLike1);
		}

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

}
