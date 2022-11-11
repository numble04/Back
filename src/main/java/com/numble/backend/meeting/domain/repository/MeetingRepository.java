package com.numble.backend.meeting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.backend.meeting.domain.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
