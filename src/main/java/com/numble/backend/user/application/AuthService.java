package com.numble.backend.user.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.jwt.enums.JwtExpirationEnums;
import com.numble.backend.common.config.security.CustomUserDetailsService;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.domain.mapper.UserCreateMapper;
import com.numble.backend.user.domain.mapper.UserMapper;
import com.numble.backend.user.domain.mapper.UserTokenMapper;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.EmailExistsException;
import com.numble.backend.user.exception.EmailNotExistsException;
import com.numble.backend.user.exception.InvalidPasswordException;
import com.numble.backend.user.exception.NicknameExistsException;
import com.numble.backend.user.exception.RefreshTokenNotFoundException;
import com.numble.backend.user.exception.TokenErrorException;
import com.numble.backend.user.exception.TokenNotExistsException;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenUtil jwtTokenUtil;
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final UserRepository userRepository;
	private final CustomUserDetailsService customUserDetailsService;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long save(final UserCreateRequest userCreateRequest) {
		checkEmail(userCreateRequest.getEmail());
		checkNickname(userCreateRequest.getNickname());
		String password = passwordEncoder.encode(userCreateRequest.getPassword());
		User user = UserCreateMapper.INSTANCE.toEntity(userCreateRequest,password);

		return userRepository.save(user).getId();
	}

	public void findEmailExists(final String email) {
		checkEmail(email);
	}

	public void findNicknameExists(final String nickname) {
		checkNickname(nickname);
	}

	private void checkEmail(final String email) {
		if (userRepository.existsByEmail(email)){
			throw new EmailExistsException();
		}
	}

	private void checkNickname(final String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new NicknameExistsException();
		}
	}

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

	public UserTokenResponse login(final UserLoginRequest userLoginRequest) {
		User user = userRepository.findByemail(userLoginRequest.getEmail())
			.orElseThrow(() -> new EmailNotExistsException());
		checkPassword(userLoginRequest.getPassword(), user.getPassword());

		UserTokenResponse response = UserTokenResponse.builder()
			.userResponse(UserMapper.INSTANCE.toDto(user))
			.accessToken(jwtTokenUtil.generateAccessToken(user.getId().toString()))
			.refreshToken(saveRefreshToken(user.getId().toString()).getRefreshToken())
			.build();

		return response;
	}

	private RefreshToken saveRefreshToken(String username) {

		return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
			jwtTokenUtil.generateRefreshToken(username), JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
	}

	private void checkPassword(String rawPassword, String findMemberPassword) {
		if (!passwordEncoder.matches(rawPassword, findMemberPassword)) {
			throw new InvalidPasswordException();
		}
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
