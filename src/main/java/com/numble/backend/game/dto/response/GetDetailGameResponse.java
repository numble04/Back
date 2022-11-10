package com.numble.backend.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailGameResponse {
	private Long id;
	private String age;
	private String capacity;
	private String img;
	private Double level;
	private Double rate;
	private String time;
	private String title;
}
