package com.numble.backend.user.domain;

import com.numble.backend.common.domain.BaseEntity;
import lombok.*;


import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name="User")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String phone;

    private String img_key;

    private String region;

    private String time;

    private String game_cate;


}
