package com.numble.backend.user.presentation;

import com.numble.backend.user.application.UserService;
import com.numble.backend.user.dto.request.UserCreateRequest;
import com.numble.backend.user.dto.request.UserRequest;
import com.numble.backend.user.dto.response.UserResponse;
import com.numble.backend.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<UserResponse> findByname(@RequestBody UserRequest userRequest) throws UserNotFoundException {
//        return ResponseEntity.ok(userService.findByname(userRequest));
//    }

    @PostMapping("/register")
    public ResponseEntity<Void> save(@RequestBody UserCreateRequest userCreateRequest) {
        Long id = userService.save(userCreateRequest);
        return ResponseEntity.created(URI.create("/api/user/register/"+id)).build();
    }

//    @PostMapping("/login")
//    public ReponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
//        return ResponseEntity.ok(userService.login(userLoginRequest));
//    }
}
