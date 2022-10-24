package com.numble.backend.user.application;

import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserMapper;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.dto.UserRequest;
import com.numble.backend.user.dto.UserResponse;
import com.numble.backend.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserResponse findByname(final UserRequest userRequest) {

       return UserMapper.INSTANCE.ToDto(
               userRepository.findByname(userRequest.getName())
               .orElseThrow(() -> new UserNotFoundException()));

    }
}
