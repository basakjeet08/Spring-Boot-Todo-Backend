package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping(UrlConstants.DELETE_USER)
    public void deleteById(@AuthenticationPrincipal User user) {
        userService.deleteById(user.getId());
    }
}
