package com.numble.backend.meeting.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.numble.backend.cafe.domain.Cafe;
import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.meeting.dto.request.MeetingUpdateRequest;
import com.numble.backend.post.domain.PostLike;
import com.numble.backend.user.exception.UserNotAuthorException;

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
@DynamicInsert
@DynamicUpdate
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

	@Column(columnDefinition="tinyint(1) default 0")
	private Boolean isFull;

	@ManyToOne(fetch = LAZY)
	private Cafe cafe;

	@OneToMany(mappedBy = "meeting", orphanRemoval = true)
	private List<MeetingLike> meetingLikes = new ArrayList<>();

	@OneToMany(mappedBy = "meeting", orphanRemoval = true)
	private List<MeetingUser> meetingUsers = new ArrayList<>();

	public void updateIsFull(int nowPersonnel) {
		if (this.capacity.equals(nowPersonnel)){
			this.isFull=true;
		} else {
			this.isFull=false;
		}
	}

	public void update(@Valid MeetingUpdateRequest meetingUpdateRequest, Cafe cafe, String img) {
		this.title = meetingUpdateRequest.getTitle();
		this.content = meetingUpdateRequest.getContent();
		this.capacity = meetingUpdateRequest.getCapacity();
		this.kakaoUrl = meetingUpdateRequest.getKakaoUrl();
		this.img = img;
		this.day = meetingUpdateRequest.getDay();
		this.time = meetingUpdateRequest.getTime();
		this.cost = meetingUpdateRequest.getCost();
		this.cafe = cafe;
	}



}
