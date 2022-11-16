package com.numble.backend.meeting.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.domain.MeetingLike;
import com.numble.backend.user.domain.User;

public interface MeetingLikeRepository extends JpaRepository<MeetingLike, Long> {

	Optional<MeetingLike> findByMeetingAndUser(Meeting meeting, User user);
}
