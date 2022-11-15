package com.numble.backend.game.dto.response;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numble.backend.game.domain.Game;
import com.numble.backend.user.domain.User;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetReviewsResponse {

	private Long id;

	private String content;

	private Double rate;

	private String nickname;

	private String profileImg;

	private Boolean myReview;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDate;

	@QueryProjection
	public GetReviewsResponse(Long id,String content,Double rate,String nickname,String profileImg,Boolean myReview,
		LocalDateTime createDate, LocalDateTime updateDate) {
		this.id = id;
		this.content = content;
		this.rate = rate;
		this.nickname = nickname;
		this.profileImg = profileImg;
		this.myReview = myReview;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
