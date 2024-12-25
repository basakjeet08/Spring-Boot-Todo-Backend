package dev.anirban.todo.service;


import dev.anirban.todo.dto.AuthDto;
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


    public User registerUser(AuthDto authDto) {
        return userService.create(authDto);
    }

    public TokenWrapper loginUser(AuthDto authDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        User savedUser = userService.findByUsername(authDto.getUsername());
        return new TokenWrapper(jwtService.generateToken(savedUser));
    }
}