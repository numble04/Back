package com.numble.backend.post.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.numble.backend.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findById(Long id);

	 List<Post> findAllById(Long id);
	 List<Post> findAllByUser(User user);

	// @Modifying
	// @Query("update Post p set p.title = ?1, p.content = ?2, p.time = ?3, u.gameCate=?4 where p.id = ?5")
	// void updateById(String img_url, String region, String time,String game_cate, Long id);

}
