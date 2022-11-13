package com.numble.backend.cafe.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numble.backend.cafe.application.CafeService;
import com.numble.backend.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

	private final CafeService cafeService;

	@GetMapping // 카페 조회
	public ResponseEntity<ResponseDto> findByKeyword(@RequestParam String keyword,
		@PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

		ResponseDto responseDto = ResponseDto.of(
			cafeService.findByKeyword(keyword,pageable));

		return ResponseEntity.ok(responseDto);

	}

}
