package com.numble.backend.cafe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeResponse {
	private Long id;
	private String name;
}
