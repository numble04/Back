package com.numble.backend.game.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThemeResponse {
	private String name;

	@QueryProjection
	public ThemeResponse(String name) {
		this.name = name;
	}
}
