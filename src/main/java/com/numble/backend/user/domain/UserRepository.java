package com.numble.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByname(String name);

	Optional<User> findBynickname(String nickname);

	Optional<User> findByemail(String email);


	Boolean existsByEmail(String email);

	@Modifying
	@Query("update User u set u.imgUrl = ?1, u.region = ?2, u.time = ?3, u.gameCate=?4 where u.id = ?5")
	void setUserInfoById(String img_url, String region, String time,String game_cate, Long id);
}
