package com.numble.backend.common.config.jwt.filter;

import com.numble.backend.common.config.jwt.JwtTokenUtil;
import com.numble.backend.common.config.security.CustomUserDetailsService;
import com.numble.backend.common.domain.access.LogoutAccessTokenRedisRepository;
import com.numble.backend.common.exception.auth.ExceptionCode;
import com.numble.backend.common.exception.auth.NoAccessTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final CustomUserDetailsService customUserDetailsService;
	private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String accessToken = getToken(request);

		try {
			// header에서 access token 추출
			if (accessToken != null && accessToken!="") {
				// access는 존재하지만 logout을 헤버린 유저인지 확인
				checkLogout(accessToken);
				// accesstoken 내부에 username 색출
				String username = jwtTokenUtil.getUsername(accessToken);

				if (username != null) {
					// user nickname과 password를 반환
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
					// username 일치 여부와 토큰 만료 여부 확인
					validateAccessToken(accessToken, userDetails);
					// 유효 검사 통과 시
					processSecurity(request, userDetails);
				}
			}
			else {
				throw new NoAccessTokenException();
			}
		} catch (NoAccessTokenException e) {
			request.setAttribute("exception", ExceptionCode.NO_TOKEN);
		} catch (SecurityException | MalformedJwtException e) {
			request.setAttribute("exception", ExceptionCode.WRONG_TYPE_TOKEN);
		} catch (ExpiredJwtException e) {
			request.setAttribute("exception", ExceptionCode.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			request.setAttribute("exception", ExceptionCode.UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			request.setAttribute("exception", ExceptionCode.WRONG_TOKEN);
		}

		filterChain.doFilter(request, response);

	}


	private String getToken(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}

	private void checkLogout(String accessToken) {
		if (logoutAccessTokenRedisRepository.existsById(accessToken)) {
			throw new IllegalArgumentException("이미 로그아웃된 회원입니다.");
		}
	}

	private void validateAccessToken(String accessToken, UserDetails userDetails) {
		if (!jwtTokenUtil.validateToken(accessToken, userDetails)) {
			throw new IllegalArgumentException("토큰 검증 실패");
		}
	}

	private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
}
