package com.numble.backend.user.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.business.BusinessException;

public class NicknameExistsException extends BusinessException {

	private static final String CLIENT_MESSAGE = "닉네임이 존재합니다.";

	public NicknameExistsException() {super(CLIENT_MESSAGE, HttpStatus.BAD_REQUEST);}
}
