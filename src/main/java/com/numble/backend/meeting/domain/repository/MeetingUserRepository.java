package com.numble.backend.meeting.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.domain.MeetingUser;
import com.numble.backend.user.domain.User;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {

	Optional<MeetingUser> findByUserAndMeeting(User user, Meeting meeting);

	@Query("select mu from MeetingUser mu where mu.user.id = :userId and mu.meeting.id = :meetingId")
	Optional<MeetingUser> findByUserIdAndMeetingId(
		@Param("userId") Long userId, @Param("meetingId") Long meetingId);
}
