package com.numble.backend.post.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.post.domain.Image;
import com.numble.backend.post.domain.ImageRepository;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.domain.mapper.PostCreateMapper;
import com.numble.backend.post.domain.mapper.PostMapper;
import com.numble.backend.post.domain.PostRepository;

import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;
import com.numble.backend.post.dto.response.PostOneResponse;
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
	private final PostRepository postRepository;

	private final ImageRepository imageRepository;
	private final AmazonS3Client amazonS3Client;
	private final UserRepository userRepository;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Transactional
	public Long save(CustomUserDetails customUserDetails, PostCreateRequest postRequest) {

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Post post = PostCreateMapper.INSTANCE.toEntity(postRequest, user);
		return postRepository.save(post).getId();
	}

	public List<PostResponse> findAll(PostType type) {

		final List<PostResponse> postResponses = postRepository.findAllByType(type)
			.stream()
			.map(PostMapper.INSTANCE::toDto)
			.collect(Collectors.toList());

		return postResponses;
	}

	public List<PostResponse> findAllByUserId(CustomUserDetails customUserDetails) {

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		final List<PostResponse> postResponses = postRepository.findAllByUser(user)
			.stream()
			.map(PostMapper.INSTANCE::toDto)
			.collect(Collectors.toList());

		return postResponses;
	}


	@Transactional
	public PostOneResponse findById(Long postId, CustomUserDetails customUserDetails) {
		PostOneResponse postOneResponse = postRepository.findOnePostById(postId, customUserDetails.getId())
			.orElseThrow(() -> new PostNotFoundException());
		return postOneResponse;
	}


	@Transactional
	public String uploadFile(MultipartFile multipartFile, Long postId) {
		validateFileExists(multipartFile);

		String fileName = buildFileName(multipartFile.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		String url = amazonS3Client.getUrl(bucketName, fileName).toString();
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		Image image = Image.builder()
			.url(url)
			.post(post)
			.build();

		imageRepository.save(image);

		return url;
	}

	private void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new RuntimeException();
		}
	}

	public static String buildFileName(String originalFileName) {
		String FILE_EXTENSION_SEPARATOR = ".";

		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		String fileExtension = originalFileName.substring(fileExtensionIndex);
		String fileName = originalFileName.substring(0, fileExtensionIndex);
		String now = String.valueOf(System.currentTimeMillis());

		return fileName + now + fileExtension;
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
