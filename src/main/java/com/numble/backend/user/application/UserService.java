package com.numble.backend.user.application;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3Client;
import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.jwt.enums.JwtExpirationEnums;
import com.numble.backend.common.domain.access.LogoutAccessToken;
import com.numble.backend.common.domain.access.LogoutAccessTokenRedisRepository;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.common.utils.S3Utils;
import com.numble.backend.post.domain.Image;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.repository.ImageRepository;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.domain.Token;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.mapper.UserInfoMapper;
import com.numble.backend.user.domain.mapper.UserMapper;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.domain.mapper.UserCreateMapper;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.request.UserUpdateRequest;
import com.numble.backend.user.dto.response.UserInfoResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.EmailExistsException;
import com.numble.backend.user.exception.EmailNotExistsException;
import com.numble.backend.user.exception.InvalidPasswordException;
import com.numble.backend.user.exception.NicknameExistsException;
import com.numble.backend.user.exception.TokenErrorException;
import com.numble.backend.user.exception.UserNotFoundException;

import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
	private final ImageRepository imageRepository;
	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;


	// 중복 검사 실시
	@Transactional
	public Long save(final UserCreateRequest userCreateRequest) {
		checkEmail(userCreateRequest.getEmail());
		checkNickname(userCreateRequest.getNickname());
		String password = passwordEncoder.encode(userCreateRequest.getPassword());
		User user = UserCreateMapper.INSTANCE.toEntity(userCreateRequest,password);

		return userRepository.save(user).getId();
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

	// 한 자리 변수명 금지, dto 빌드 직접
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

	private void checkPassword(String rawPassword, String findMemberPassword) {
		if (!passwordEncoder.matches(rawPassword, findMemberPassword)) {
			throw new InvalidPasswordException();
		}
	}

	private RefreshToken saveRefreshToken(String username) {

		return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
			jwtTokenUtil.generateRefreshToken(username), JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
	}

	public Long logout(String accessToken, String refreshToken) {
		checkToken(accessToken, refreshToken);
		String token = accessToken.substring(7);
		String username = jwtTokenUtil.getUsername(token);
		Long ms = jwtTokenUtil.getRemainMilliSeconds(token);

		logoutAccessTokenRedisRepository.save(
			LogoutAccessToken.of(token, username, ms)
		);
		refreshTokenRedisRepository.deleteById(username);

		return userRepository.findBynickname(username)
			.orElseThrow(() -> new UserNotFoundException()).getId();
	}

	private void checkToken(String accessToken, String refreshToken) {
		if (!(StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) && accessToken.startsWith("Bearer "))) {
			throw new TokenErrorException();
		}
	}

	public UserInfoResponse findById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());

		return UserInfoMapper.INSTANCE.toDto(user);
	}

	@Transactional
	public void updateById(Long id,UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());

		if (userUpdateRequest.getNickname() != "" &&
			userRepository.existsByNickname(userUpdateRequest.getNickname())) {
			throw new NicknameExistsException();
		}

		user.updateUser(userUpdateRequest);
	}

	@Transactional
	public void updateImg(Long id,MultipartFile multipartFile) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());

		uploadFile(multipartFile,user);
	}

	@Transactional
	public void uploadFile(MultipartFile multipartFile, User user) {
		if (multipartFile == null) {
			return;
		}

		String fileName = S3Utils.uploadFileS3(amazonS3Client, bucketName, multipartFile);
		user.updateImg(amazonS3Client.getUrl(bucketName, fileName).toString());

	}

	@Transactional
	public void deleteById(String accessToken, String refreshToken, Long id) {
		checkToken(accessToken, refreshToken);
		String token = accessToken.substring(7);
		String username = jwtTokenUtil.getUsername(token);
		Long ms = jwtTokenUtil.getRemainMilliSeconds(token);

		logoutAccessTokenRedisRepository.save(
			LogoutAccessToken.of(token, username, ms)
		);
		refreshTokenRedisRepository.deleteById(username);
		userRepository.deleteById(id);

	}

}
