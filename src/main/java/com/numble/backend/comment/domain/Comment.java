package com.numble.backend.comment.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.common.exception.business.InvalidFieldException;
import com.numble.backend.post.domain.Post;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.exception.UserNotAuthorException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Comment extends BaseEntity {
	@Column(nullable = false)
	private String content;


	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parentId")
	private Comment parent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	private List<Comment> children = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId")
	private Post post;


	private void validateContent(final String content) {
		if (content == null || content.isBlank()) {
			throw new InvalidFieldException("댓글 내용은 공백이나 null일 수 없습니다.");
		}
	}

	public void validateMemberIsAuthor(final Long userId) {
		final boolean isNotAuthor = !this.user.getId().equals(userId);
		if (isNotAuthor) {
			throw new UserNotAuthorException();
		}
	}

	public void updateContent(final String content, final Long userId) {
		validateContent(content);
		validateMemberIsAuthor(userId);
		this.content = content;
	}
}
