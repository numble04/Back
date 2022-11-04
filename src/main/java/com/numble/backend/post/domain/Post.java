package com.numble.backend.post.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.numble.backend.comment.domain.Comment;
import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.common.exception.business.InvalidFieldException;
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
@DynamicInsert
@DynamicUpdate
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private PostType type;

    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

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
