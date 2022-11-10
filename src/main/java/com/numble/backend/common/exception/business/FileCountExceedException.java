package com.numble.backend.common.exception.business;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class FileCountExceedException extends BusinessException {
	private static final String CLIENT_MESSAGE = "이미지 업로드 갯수를 초과했습니다";

	public FileCountExceedException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
