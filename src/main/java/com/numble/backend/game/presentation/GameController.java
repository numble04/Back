package com.numble.backend.game.presentation;


import java.net.URI;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.numble.backend.game.dto.request.PostGameReviewRequest;
import com.numble.backend.game.dto.request.UpdateGameReviewRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	private final GameReviewService gameReviewService;

	@GetMapping
	public ResponseEntity<ResponseDto> findAll(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam(defaultValue = "오름차순") String type) {
		ResponseDto responseDto = ResponseDto.of(gameService.findAll(pageable,type));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/search")
	public ResponseEntity<ResponseDto> findAllBySearch(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam String title, @RequestParam(defaultValue = "오름차순") String type) {
		ResponseDto responseDto = ResponseDto.of(gameService.findAllBySearch(pageable,'%'+title+'%',type));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{Id}")
	public ResponseEntity<ResponseDto> findById(@PathVariable("Id") final Long id) {
		ResponseDto responseDto = ResponseDto.of(gameService.findById(id));

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/{Id}/reviews")
	public ResponseEntity<ResponseDto> findReviewsById(@PathVariable("Id") final Long id,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		ResponseDto responseDto = ResponseDto.of(gameReviewService.findReviewsByGameId(id,pageable));

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/{Id}/review")
	public ResponseEntity<Void> findReviewsById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("Id") Long gameId,
		@RequestBody PostGameReviewRequest postGameReviewRequest) {
		Long userId = customUserDetails.getId();
		Long id = gameReviewService.save(gameId,userId,postGameReviewRequest);

		return ResponseEntity.created(URI.create("/api/games/"+gameId+"/review/" + id)).build();
	}

	@PutMapping("/{Id}/review/{reviewId}")
	public ResponseEntity<Void> UpdateReviewById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("Id") Long gameId, @PathVariable("reviewId") Long reviewId,
		@RequestBody UpdateGameReviewRequest updateGameReviewRequest) {
		Long userId = customUserDetails.getId();
		gameReviewService.updateById(userId,reviewId,updateGameReviewRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{Id}/review/{reviewId}")
	public ResponseEntity<Void> DeleteReviewById(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable("Id") Long gameId, @PathVariable("reviewId") Long reviewId) {
		Long userId = customUserDetails.getId();
		gameReviewService.deleteById(userId,reviewId);

		return ResponseEntity.noContent().build();
	}

}
