package com.numble.backend.game.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameDetailResponse {

	private Long id;
	private String age;
	private String capacity;
	private String img;
	private Double level;
	private Double rate;
	private String time;
	private String title;
	private List<ThemeResponse> themeList = new ArrayList<>();

}