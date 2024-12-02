package dev.anirban.todo.service;


import dev.anirban.todo.dto.TokenWrapper;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;


    public User registerUser(User user) {
        return userService.create(user);
    }

    public TokenWrapper loginUser(String username, String password) {

        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User savedUser = userService.findByUsername(username);
        return new TokenWrapper(jwtService.generateToken(savedUser));
    }
}