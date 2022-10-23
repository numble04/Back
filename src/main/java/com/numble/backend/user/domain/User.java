package com.numble.backend.user.domain;

import com.numble.backend.common.domain.BaseEntity;
import lombok.*;


import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    private String name;


}
