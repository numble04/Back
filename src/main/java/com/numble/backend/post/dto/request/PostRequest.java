package com.numble.backend.post.dto.request;

import javax.persistence.Column;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostRequest {
    private String title;
    private String contents;
    private Integer type;
}
