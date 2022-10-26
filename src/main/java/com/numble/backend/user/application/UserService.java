package com.numble.backend.user.application;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.jwt.enums.JwtExpirationEnums;
import com.numble.backend.common.domain.access.LogoutAccessToken;
import com.numble.backend.common.domain.access.LogoutAccessTokenRedisRepository;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserMapper;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserLoginRequest;
import com.numble.backend.user.dto.request.UserRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;
import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


//    public UserResponse findByname(final UserRequest userRequest) {
//
//       return UserMapper.INSTANCE.ToDto(
//               userRepository.findByname(userRequest.getName())
//               .orElseThrow(() -> new UserNotFoundException()));
//
//    }

    @Transactional
    public Long save(final UserCreateRequest userCreateRequest) {
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        return userRepository.save(UserMapper.INSTANCE.ToEntity(userCreateRequest)).getId();
    }

    public UserTokenResponse login(final UserLoginRequest userLoginRequest) {
        User user = userRepository.findByemail(userLoginRequest.getEmail()).orElseThrow(()->new UserNotFoundException());
        checkPassword(userLoginRequest.getPassword(),user.getPassword());
        String accessToken = jwtTokenUtil.generateAccessToken(user.getNickname());
        RefreshToken refreshToken = saveRefreshToken(user.getNickname());

        return UserTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
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

    public Long logout(String token) {
        String stoken = token.substring(7);
        String username = jwtTokenUtil.getUsername(stoken);
        Long ms = jwtTokenUtil.getRemainMilliSeconds(stoken);
        System.out.println("남은 시간: "+ms);
        logoutAccessTokenRedisRepository.save(
                LogoutAccessToken.of(stoken,username,ms)
        );

        return userRepository.findBynickname(username)
                    .orElseThrow(() -> new UserNotFoundException()).getId();
    }

}
