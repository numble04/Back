package com.numble.backend.cafe.dto.response;

import org.locationtech.jts.geom.Point;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeResponse {
	private Long id;
	private String name;
}
