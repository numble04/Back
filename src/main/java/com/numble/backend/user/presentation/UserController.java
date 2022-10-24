package com.numble.backend.user.presentation;

import com.numble.backend.user.application.UserService;
import com.numble.backend.user.dto.UserRequest;
import com.numble.backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> findByname(@RequestBody UserRequest userRequest) {
        final UserResponse response = userService.findByname(userRequest);

        return ResponseEntity.ok(response);
    }
}
