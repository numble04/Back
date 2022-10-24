package com.numble.backend.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequest {
    private String userId;
    private String name;
    private String password;
}
