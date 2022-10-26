package com.numble.backend.user.application;

import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserMapper;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long save(final UserCreateRequest userCreateRequest) {
        return userRepository.save(UserMapper.INSTANCE.ToEntity(userCreateRequest)).getId();
    }

//    public UserLoginResponse login(final UserLoginRequest userLoginRequest) {
//
////        return UserMapper.INSTANCE.ToDto(
////                userRepository.save().orElseThrow(() -> new UserNotCreatedException());
////        )
//    }
}
