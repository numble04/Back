package com.numble.backend.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByname(String name);

	Optional<User> findBynickname(String nickname);

	Optional<User> findByemail(String email);

	Boolean existsByEmail(String email);

	Boolean existsByNickname(String nickname);


}
