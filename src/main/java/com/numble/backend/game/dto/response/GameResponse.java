package com.numble.backend.game.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameResponse {
	private Long id;
	private String title;
	private String img;
	private Double rate;
	private Double level;

	@QueryProjection
	public GameResponse(Long id,String title,String img,Double rate,Double level) {
		this.id = id;
		this.title = title;
		this.img = img;
		this.rate = rate;
		this.level = level;
	}
}