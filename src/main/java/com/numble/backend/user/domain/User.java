package com.numble.backend.user.domain;

import com.numble.backend.common.domain.BaseEntity;
import lombok.*;


import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name = "userId_unique", columnNames = {"userId"})})
public class User extends BaseEntity {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;


}
