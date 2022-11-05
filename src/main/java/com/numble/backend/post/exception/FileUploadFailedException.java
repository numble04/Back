package com.numble.backend.post.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class FileUploadFailedException extends BusinessException {
	private static final String CLIENT_MESSAGE = "이미지 업로드에 실패했습니다.";
	public FileUploadFailedException() {
		super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
