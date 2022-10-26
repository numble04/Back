package com.numble.backend.post.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.post.application.PostService;
import com.numble.backend.post.dto.request.PostCreateRequest;
import com.numble.backend.post.dto.response.PostResponse;
import com.numble.backend.post.dto.response.PostResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


	@PostMapping
	public ResponseEntity<Void> save(@RequestBody PostCreateRequest postRequest) {
		Long userId= Long.valueOf(1);
	    final Long id = postService.save(userId, postRequest);

	    return ResponseEntity.created(URI.create("/api/posts/"+id)).build();
	}

	@GetMapping
	public ResponseEntity<PostResponses> findAll() {
		final PostResponses postResponses = postService.findAll();

		return ResponseEntity.ok(postResponses);
	}

	@GetMapping("/2")
	public ResponseEntity<PostResponses> findAllByUserId() {
		Long userId= Long.valueOf(1);
		final PostResponses postResponses = postService.findAllByUserId(userId);

		return ResponseEntity.ok(postResponses);
	}

	@GetMapping("/3/{postId}")
	public ResponseEntity<PostResponse> findById(@PathVariable final Long postId) {

		final PostResponse postResponse = postService.findById(postId);

		return ResponseEntity.ok(postResponse);
	}

}
