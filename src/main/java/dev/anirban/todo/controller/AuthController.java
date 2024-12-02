package dev.anirban.todo.controller;


import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.dto.TokenWrapper;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(UrlConstants.REGISTER_USER)
    public User registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping(UrlConstants.LOGIN_USER)
    public TokenWrapper loginUser(@RequestBody User user) {
        return authService.loginUser(user.getUsername(), user.getPassword());
    }
}