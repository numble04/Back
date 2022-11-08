package com.numble.backend.game.presentation;


import javax.websocket.server.PathParam;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.dto.ResponseDto;
import com.numble.backend.game.application.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	@GetMapping
	public ResponseEntity<ResponseDto> findAll(Pageable pageable) {
		ResponseDto responseDto = ResponseDto.of(gameService.findAll(pageable).getContent());

		return ResponseEntity.ok(responseDto);
	}
}
