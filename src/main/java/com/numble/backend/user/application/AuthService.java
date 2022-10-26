package com.numble.backend.user.application;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.domain.refresh.RefreshToken;
import com.numble.backend.common.domain.refresh.RefreshTokenRedisRepository;
import com.numble.backend.user.dto.response.UserTokenResponse;
import com.numble.backend.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public UserTokenResponse reissue(String refreshToken) {
        String username = jwtTokenUtil.getUsername(refreshToken.substring(7));
        System.out.println("refresh token username: "+username);
        RefreshToken refreshToken1 = refreshTokenRedisRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException());

        return UserTokenResponse.builder()
                .accessToken(jwtTokenUtil.generateAccessToken(username))
                .refreshToken(refreshToken1.getRefreshToken())
                .build();
    }

}
