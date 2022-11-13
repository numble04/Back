package com.numble.backend.cafe.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.cafe.application.CafeService;
import com.numble.backend.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cafe")
@RequiredArgsConstructor
public class CafeController {

	private final CafeService cafeService;

	@GetMapping // 카페 조회
	public ResponseEntity<ResponseDto> findCafe(@RequestParam String keyword, Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(
			cafeService.findCafe(keyword,pageable));

		return ResponseEntity.ok(responseDto);

	}

}
