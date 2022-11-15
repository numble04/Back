package com.numble.backend.meeting.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.exception.UserNotAuthorException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingUser extends BaseEntity {

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "meetingId")
	private Meeting meeting;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean isLeader;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean isApproved;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean isRejected;

	public MeetingUser(User user, Meeting meeting, Boolean isLeader, Boolean isApproved, Boolean isRejected) {
		this.user = user;
		this.meeting = meeting;
		this.isLeader = isLeader;
		this.isApproved = isApproved;
		this.isRejected = isRejected;
	}

	public void updateApprove() {
		this.isApproved = true;
	}

	public void updateReject() {
		this.isRejected = true;
	}

	public void updateBan() {
		this.isRejected = true;
	}
}
