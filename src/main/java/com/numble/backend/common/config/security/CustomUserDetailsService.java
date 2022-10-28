package com.numble.backend.common.config.security;

import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return CustomUserDetails.of(
                userRepository.findBynickname(username)
                        .orElseThrow(() -> new UserNotFoundException())
        );
    }
}
