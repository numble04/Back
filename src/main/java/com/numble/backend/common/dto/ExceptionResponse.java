package com.numble.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final String message;
}
