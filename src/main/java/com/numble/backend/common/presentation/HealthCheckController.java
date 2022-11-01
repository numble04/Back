package com.numble.backend.common.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthCheck")
public class HealthCheckController {

	@GetMapping
	public String healthCheck() {

		return "보드커 서버 정상 작동";
	}
}
