package com.numble.backend.meeting.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.domain.User;

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

	public MeetingUser(User user, Meeting meeting, Boolean isLeader, Boolean isApproved) {
		this.user = user;
		this.meeting = meeting;
		this.isLeader = isLeader;
		this.isApproved = isApproved;
	}
}
