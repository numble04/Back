package com.numble.backend.common.domain.access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Builder
@Getter
@RedisHash("logoutAccessToken")
@AllArgsConstructor
@ToString
public class LogoutAccessToken {
    @Id
    private String id;

    private String username;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessToken of(String accessToken, String username, Long remainingMilliSeconds) {
        return LogoutAccessToken.builder()
                .id(accessToken)
                .username(username)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
