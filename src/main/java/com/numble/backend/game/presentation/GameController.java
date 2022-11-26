package com.numble.backend.game.presentation;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.game.application.GameReviewService;
import com.numble.backend.game.application.GameService;
import com.numble.backend.game.dto.request.GameReviewCreateRequest;
import com.numble.backend.game.dto.request.GameReviewUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	private final GameReviewService gameReviewService;

	@Cacheable(cacheNames = "game", cacheManager = "caffeineCacheManager")
	@GetMapping
	public ResponseEntity<ResponseDto> findAll(@PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
		ResponseDto responseDto = ResponseDto.of(gameService.findAll(pageable));

		return ResponseEntity.ok(responseDto);
	}


	@GetMapping("/search")
	public ResponseEntity<ResponseDto> findAllBySearch(@PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
		@RequestParam("title") String title) {
		ResponseDto responseDto = ResponseDto.of(gameService.findAllBySearch(pageable,title));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<ResponseDto> findById(@PathVariable("gameId") final Long id) {
		ResponseDto responseDto = ResponseDto.of(gameService.findById(id));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<ResponseDto> findReviewsById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("gameId") final Long gameId,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		ResponseDto responseDto = ResponseDto.of(
			gameReviewService.findReviewsByGameId(customUserDetails.getId(),gameId,pageable));

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/{gameId}/review")
	public ResponseEntity<Void> findReviewsById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("gameId") Long gameId,
		@RequestBody GameReviewCreateRequest gameReviewCreateRequest) {
		Long userId = customUserDetails.getId();
		Long id = gameReviewService.save(gameId,userId,gameReviewCreateRequest);

		return ResponseEntity.created(URI.create("/api/games/"+gameId+"/review/" + id)).build();
	}

	@PutMapping("/{gameId}/review/{reviewId}")
	public ResponseEntity<Void> UpdateReviewById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("gameId") Long gameId, @PathVariable("reviewId") Long reviewId,
		@RequestBody GameReviewUpdateRequest gameReviewUpdateRequest) {
		Long userId = customUserDetails.getId();
		gameReviewService.updateById(userId,reviewId,gameReviewUpdateRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{gameId}/review/{reviewId}")
	public ResponseEntity<Void> DeleteReviewById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("gameId") Long gameId, @PathVariable("reviewId") Long reviewId) {
		Long userId = customUserDetails.getId();
		gameReviewService.deleteById(userId,reviewId);

		return ResponseEntity.noContent().build();
	}

}
