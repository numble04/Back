package com.numble.backend.meeting.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.numble.backend.cafe.domain.Cafe;
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
public class Meeting extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Integer capacity;

	private String kakaoUrl;

	private String img;

	@Column(nullable = false)
	private LocalDateTime day;

	@Column(nullable = false)
	private Integer time;

	@Column(nullable = false)
	private Integer cost;

	@ManyToOne(fetch = LAZY)
	private Cafe cafe;



}
