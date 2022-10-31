package com.numble.backend.post.dto.request;

import javax.persistence.Column;

import com.numble.backend.post.domain.PostType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostRequest {
    private String title;
    private String content;
    private PostType type;
}
