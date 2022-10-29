package com.numble.backend.post.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.common.exception.InvalidFieldException;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.exception.UserNotAuthorException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    private Integer type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Image> images;


    private void validateContent(final String content) {
        if (content == null || content.isBlank()) {
            throw new InvalidFieldException("게시글 내용은 공백이나 null일 수 없습니다.");
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
