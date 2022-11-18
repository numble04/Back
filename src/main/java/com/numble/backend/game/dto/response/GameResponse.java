package com.numble.backend.game.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameResponse {
	private Long id;
	private String title;
	private String img;
	private String capacity;
	private String time;
	private String age;
	private Double rate;
	private Double level;

	private List<ThemeResponse> themeList = new ArrayList<>();

	@QueryProjection
	public GameResponse(Long id,String title,String img,String capacity,String time,String age,Double rate,Double level) {
		this.id = id;
		this.title = title;
		this.img = img;
		this.capacity = capacity;
		this.time = time;
		this.age = age;
		this.rate = rate;
		this.level = level;
	}
}