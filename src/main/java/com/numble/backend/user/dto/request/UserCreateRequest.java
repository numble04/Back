package com.numble.backend.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickname;

    public void setPassword(String password) {
        this.password = password;
    }
}
