package com.numble.backend.post.dto.response;

import com.numble.backend.user.dto.UserResponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String contents;
    private Integer type;
    private UserResponse userResponse;
}
