package com.numble.backend.cafe.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeResponse {
	private Long id;
	private String name;

	@QueryProjection
	public CafeResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
