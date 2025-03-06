package com.markguiang.backend.auth;

import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User registerUser(@RequestBody User user) {
        return this.userService.registerUser(user);
    }
}
