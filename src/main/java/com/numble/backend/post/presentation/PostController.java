package com.numble.backend.post.presentation;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.Validator;

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
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.post.application.PostService;
import com.numble.backend.post.domain.PostType;
import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.request.PostUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart(value = "postRequest") @Valid PostCreateRequest postRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {

		final Long id = postService.save(customUserDetails, postRequest, multipartFiles);

		return ResponseEntity.created(URI.create("/api/posts/" + id)).build();
	}


	@GetMapping
	public ResponseEntity<ResponseDto> findAll(@RequestParam("type") PostType type) {
		ResponseDto responseDto = ResponseDto.of(postService.findAll(type));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/my")
	public ResponseEntity<ResponseDto> findAllByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(postService.findAllByUserId(customUserDetails));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<ResponseDto> findById(@PathVariable final Long postId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(postService.findById(postId, customUserDetails));
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {

		postService.updateById(customUserDetails, postId, postUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{postId}/like")
	public ResponseEntity<Void> updateLikeById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {

		postService.updateLikeById(customUserDetails, postId, postUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId) {

		postService.deleteById(customUserDetails, postId);

		return ResponseEntity.noContent().build();
	}
}
