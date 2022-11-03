package com.numble.backend.comment.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.comment.application.CommentService;
import com.numble.backend.comment.dto.request.CommentRequest;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/{postId}")
	public ResponseEntity<ResponseDto> findAllByPostId(@PathVariable final Long postId) {
		ResponseDto responseDto=ResponseDto.of(commentService.findAllByPostId(postId));
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/{postId}")
	public ResponseEntity<Void> saveByPostId(@PathVariable final Long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CommentRequest commentRequest) {


		final Long id = commentService.saveByPostId(postId, commentRequest, customUserDetails);

		return ResponseEntity.created(URI.create("/api/comments/" + id)).build();
	}
}
