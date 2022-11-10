package com.numble.backend.cafe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.locationtech.jts.geom.Point;

import com.numble.backend.common.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cafe extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Point point;

	@Column(nullable = false)
	private String address;
}
