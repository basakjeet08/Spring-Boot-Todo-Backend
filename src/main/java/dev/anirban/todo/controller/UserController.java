package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(UrlConstants.CREATE_USER)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(UrlConstants.FIND_ALL_USER)
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(UrlConstants.FIND_USER_BY_ID)
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @DeleteMapping(UrlConstants.DELETE_USER_BY_ID)
    public void deleteById(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
