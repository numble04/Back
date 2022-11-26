package com.numble.backend.post.presentation;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

	@PostMapping // 게시글 등록
	public ResponseEntity<Void> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart(value = "postRequest") @Valid PostCreateRequest postRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {

		final Long id = postService.save(customUserDetails, postRequest, multipartFiles);

		return ResponseEntity.created(URI.create("/api/posts/" + id)).build();
	}

	@GetMapping // 게시글 조회
	public ResponseEntity<ResponseDto> findAllByType(@RequestParam("type") PostType type,
		@PageableDefault(size = 10) Pageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(postService.findAllByType(type, pageable, customUserDetails));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{postId}") //게시글 상세 조회
	public ResponseEntity<ResponseDto> findById(@PathVariable final Long postId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(postService.findById(postId, customUserDetails));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/my") //내 게시글 조회
	public ResponseEntity<ResponseDto> findAllByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PageableDefault(size = 10) Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(postService.findAllByUserId(customUserDetails, pageable));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/search")
	public ResponseEntity<ResponseDto> findAllBySearch(@RequestParam("searchWord") String searchWord,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		ResponseDto responseDto = ResponseDto.of(
			postService.findAllBySearch(searchWord, pageable, customUserDetails));

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{postId}") //게시글 수정
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId, @RequestPart(value = "postRequest") @Valid PostUpdateRequest postUpdateRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {

		postService.updateById(customUserDetails, postId, postUpdateRequest, multipartFiles);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/like") //좋아요한 게시글 조회
	public ResponseEntity<ResponseDto> findAllByLike(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PageableDefault(size = 10) Pageable pageable) {
		ResponseDto responseDto = ResponseDto.of(postService.findAllByLike(customUserDetails, pageable));

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{postId}/like") //좋아요
	public ResponseEntity<Void> updateLikeById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId) {
		postService.updateLikeById(customUserDetails, postId);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> updateById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable final Long postId) {
		postService.deleteById(customUserDetails, postId);

		return ResponseEntity.noContent().build();
	}
}
