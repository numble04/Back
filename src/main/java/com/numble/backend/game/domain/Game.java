package com.numble.backend.game.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "uk_game_title", columnNames = {"title"}),
	@UniqueConstraint(name = "uk_game_rate", columnNames = {"rate"}),
	@UniqueConstraint(name = "uk_game_level", columnNames = {"level"})
})
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

	@JsonManagedReference
	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
	private List<Theme> themeList;
 }
