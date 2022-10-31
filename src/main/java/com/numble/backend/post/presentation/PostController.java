package com.numble.backend.post.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.post.application.PostService;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.post.dto.response.PostResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


	@PostMapping
	public ResponseEntity<Void> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,

		@RequestBody @Valid PostCreateRequest postRequest) {

	    final Long id = postService.save(customUserDetails, postRequest);

	    return ResponseEntity.created(URI.create("/api/posts/"+id)).build();
	}

	@PostMapping("/upload")
	public String uploadFile(
		@RequestPart(value = "file") MultipartFile multipartFile) {
		Long postId=Long.valueOf(1);
		return postService.uploadFile(multipartFile, postId);
	}

	@GetMapping
	public ResponseEntity<PostResponses> findAll(@RequestParam("type") PostType type) {
		final PostResponses postResponses = postService.findAll(type);

		return ResponseEntity.ok(postResponses);
	}

	@GetMapping("/2")
	public ResponseEntity<PostResponses> findAllByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		final PostResponses postResponses = postService.findAllByUserId(customUserDetails);

		return ResponseEntity.ok(postResponses);
	}

	@GetMapping("/3/{postId}")
	public ResponseEntity<PostResponse> findById(@PathVariable final Long postId) {

		final PostResponse postResponse = postService.findById(postId);

		return ResponseEntity.ok(postResponse);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {

		postService.updateById(customUserDetails, postId,postUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{postId}/like")
	public ResponseEntity<Void> updateLikeById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {

		postService.updateLikeById(customUserDetails, postId,postUpdateRequest);

		return ResponseEntity.noContent().build();
	}



	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId) {

		postService.deleteById(customUserDetails, postId);

		return ResponseEntity.noContent().build();
	}
}
