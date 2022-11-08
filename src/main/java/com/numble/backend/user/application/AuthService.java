package com.numble.backend.user.application;

import java.util.Optional;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.security.CustomUserDetailsService;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.domain.mapper.UserMapper;
import com.numble.backend.user.domain.mapper.UserTokenMapper;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.RefreshTokenNotFoundException;
import com.numble.backend.user.exception.TokenErrorException;
import com.numble.backend.user.exception.TokenNotExistsException;
import com.numble.backend.user.exception.UserNotFoundException;

import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenUtil jwtTokenUtil;
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final UserRepository userRepository;
	private final CustomUserDetailsService customUserDetailsService;

	@Transactional
	public UserTokenResponse reissue(String refreshToken) {
		checkRefresh(refreshToken);
		String id = jwtTokenUtil.getUsername(refreshToken);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(id);
		validateRefreshToken(refreshToken,userDetails);

		RefreshToken refreshToken1 = refreshTokenRedisRepository.findById(id)
			.orElseThrow(() -> new RefreshTokenNotFoundException());

		Token token = Token.builder()
			.accessToken(jwtTokenUtil.generateAccessToken(id))
			.refreshToken(refreshToken1.getRefreshToken())
			.build();

		User user = userRepository.findById(Long.parseLong(id))
			.orElseThrow(() -> new UserNotFoundException());


		return UserTokenMapper.INSTANCE.toDto(token, UserMapper.INSTANCE.toDto(user));
	}

	private void checkRefresh(String refreshToken) {
		if (!(StringUtils.hasText(refreshToken))) {
			throw new TokenNotExistsException();
		}
	}

	private void validateRefreshToken(String refreshToken,UserDetails userDetails) {
		if (!jwtTokenUtil.validateToken(refreshToken, userDetails)) {
			throw new TokenErrorException();
		}
	}

}
