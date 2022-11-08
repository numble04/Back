package com.numble.backend.game.dto.response;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.numble.backend.game.domain.Theme;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetGameResponse {
	private Long id;
	private String title;
	private String img;
	private String capacity;
	private String time;
	private String age;
	private Double rate;
	private Double level;
	private List<Theme> themeList;
}
