package com.numble.backend.user.application;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.jwt.enums.JwtExpirationEnums;
import com.numble.backend.common.domain.access.LogoutAccessToken;
import com.numble.backend.common.domain.access.LogoutAccessTokenRedisRepository;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserMapper;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.domain.mapper.UserCreateMapper;
import com.numble.backend.user.domain.mapper.UserLoginMapper;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.request.UserRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;

import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
	private final JwtTokenUtil jwtTokenUtil;
	private final PasswordEncoder passwordEncoder;

	public UserResponse findByname(final String accessToken) {
		String username = jwtTokenUtil.getUsername(accessToken);
		return UserMapper.INSTANCE.ToDto(
			userRepository.findBynickname(username)
				.orElseThrow(() -> new UserNotFoundException()));
	}

	@Transactional
	public Long save(final UserCreateRequest userCreateRequest) {
		userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
		System.out.println(userCreateRequest.getEmail());
		System.out.println(UserCreateMapper.INSTANCE.ToEntity(userCreateRequest).getId());
		return userRepository.save(UserCreateMapper.INSTANCE.ToEntity(userCreateRequest)).getId();
	}

	public UserTokenResponse login(final UserLoginRequest userLoginRequest) {
		User user = userRepository.findByemail(userLoginRequest.getEmail())
			.orElseThrow(() -> new UserNotFoundException());
		checkPassword(userLoginRequest.getPassword(), user.getPassword());

		Token t = Token.builder()
			.accessToken(jwtTokenUtil.generateAccessToken(user.getNickname()))
			.refreshToken(saveRefreshToken(user.getNickname()).getRefreshToken())
			.build();
		return UserLoginMapper.INSTANCE.ToDto(t);
	}

	private void checkPassword(String rawPassword, String findMemberPassword) {
		if (!passwordEncoder.matches(rawPassword, findMemberPassword)) {
			throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
		}
	}

	private RefreshToken saveRefreshToken(String username) {
		return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
			jwtTokenUtil.generateRefreshToken(username), JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
	}

	public Long logout(String accessToken, String refreshToken) {
		checkToken(accessToken, refreshToken);
		String t1 = accessToken.substring(7);
		String username = jwtTokenUtil.getUsername(t1);
		Long ms = jwtTokenUtil.getRemainMilliSeconds(t1);

		logoutAccessTokenRedisRepository.save(
			LogoutAccessToken.of(t1, username, ms)
		);
		refreshTokenRedisRepository.deleteById(username);

		return userRepository.findBynickname(username)
			.orElseThrow(() -> new UserNotFoundException()).getId();
	}

	private void checkToken(String accessToken, String refreshToken) {
		if (!(StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) && accessToken.startsWith(
			"Bearer "))) {
			throw new DecodingException("토큰이 잘못됨");
		}
	}

}
