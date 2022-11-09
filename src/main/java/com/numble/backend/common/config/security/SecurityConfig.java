package com.numble.backend.common.config.security;

import com.numble.backend.common.config.jwt.filter.JwtAuthenticationFilter;
import com.numble.backend.common.config.jwt.handler.JwtAccessDeniedHandler;
import com.numble.backend.common.config.jwt.handler.JwtEntryPoint;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	// jwt 전용 exception handling
	private final JwtEntryPoint jwtEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	// jwt 전용 인증 filter
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// 해당 주소들은 인증 무시
		return (web) -> web.ignoring()
			.antMatchers("/api/auth/**","/api/healthCheck");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf()
			.disable()
			// 스프링 시큐리티에 jwt가 추가되었으므로
			// custom한 jwt exception handler를 추가
			.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.and()
			// 스프링 시큐리티는 세션을 사용하지만 jwt 추가할 시는 필요없음
			// 따라서 session 설정을 stateless로
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/api/users/**","/api/auth/reissue","/api/posts/**","/api/comments/**").authenticated()
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
