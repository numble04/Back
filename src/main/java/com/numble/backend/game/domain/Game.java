package com.numble.backend.game.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.numble.backend.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Game extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String img;
	private String capacity;
	private String time;
	private String age;
	private Double rate;
	private Double level;

	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
	private List<Theme> themeList;
 }
