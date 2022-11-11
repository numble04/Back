package com.numble.backend.meeting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.meeting.domain.MeetingUser;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
}
