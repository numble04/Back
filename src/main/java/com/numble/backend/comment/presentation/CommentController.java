package com.numble.backend.comment.presentation;

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
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.comment.application.CommentService;
import com.numble.backend.comment.dto.request.CommentCreateRequest;
import com.numble.backend.comment.dto.request.CommentUpdateRequest;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/my")
	public ResponseEntity<ResponseDto> findAllByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		ResponseDto responseDto = ResponseDto.of(commentService.findAllByUserId(customUserDetails));
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/{postId}") //댓글 작성
	public ResponseEntity<Void> saveByPostId(@PathVariable final Long postId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CommentCreateRequest commentCreateRequest) {

		final Long id = commentService.saveByPostId(postId, commentCreateRequest, customUserDetails);

		return ResponseEntity.created(URI.create("/api/comments/" + id)).build();
	}

	@PostMapping("/child/{id}") //대댓글 작성
	public ResponseEntity<Void> saveChildById(@PathVariable final Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CommentCreateRequest commentCreateRequest) {

		final Long comentId = commentService.saveChildById(id, commentCreateRequest, customUserDetails);

		return ResponseEntity.created(URI.create("/api/comments/child/" + comentId)).build();
	}

	@PutMapping("/{id}") //댓글 수정
	public ResponseEntity<Void> updateById(@PathVariable final Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {

		commentService.updateById(id, commentUpdateRequest, customUserDetails);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}") //댓글 삭제
	public ResponseEntity<Void> deleteById(@PathVariable final Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		commentService.deleteById(id, customUserDetails);

		return ResponseEntity.noContent().build();
	}
}
