package com.numble.backend.post.dto.response;

import com.numble.backend.post.domain.PostType;
import com.numble.backend.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private PostType type;
    private UserResponse userResponse;
}
