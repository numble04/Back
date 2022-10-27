package com.numble.backend.user.application;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.mapper.UserLoginMapper;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;

import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenUtil jwtTokenUtil;
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;

	@Transactional
	public UserTokenResponse reissue(String refreshToken) {
		checkRefresh(refreshToken);
		String username = jwtTokenUtil.getUsername(refreshToken);
		RefreshToken refreshToken1 = refreshTokenRedisRepository.findById(username)
			.orElseThrow(() -> new UserNotFoundException());

		Token t = Token.builder()
			.accessToken(jwtTokenUtil.generateAccessToken(username))
			.refreshToken(refreshToken1.getRefreshToken())
			.build();

		return UserLoginMapper.INSTANCE.ToDto(t);
	}

	private void checkRefresh(String refreshToken) {
		if (!(StringUtils.hasText(refreshToken))) {
			throw new DecodingException("토큰 내용 없음");
		}
	}

}
