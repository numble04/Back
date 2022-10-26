package com.numble.backend.post.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.user.domain.User;

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
    private String contents;

    @Column(nullable = false)
    private Integer type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;


}
