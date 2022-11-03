package com.numble.backend.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto<T> {

	private final T data;

	public static <T> ResponseDto of(T data) {
		return ResponseDto.builder()
			.data(data)
			.build();
	}
}
