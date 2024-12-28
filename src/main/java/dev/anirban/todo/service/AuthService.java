package dev.anirban.todo.service;

import dev.anirban.todo.dto.AuthDto;
import dev.anirban.todo.dto.TokenWrapper;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.RequestNotAuthorized;
import dev.anirban.todo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;


    public User registerUser(AuthDto authDto) {
        return userService.create(authDto);
    }

    private TokenWrapper generateTokenWrapper(UserDetails user) {
        String token = jwtService.generateToken(user, new Date(System.currentTimeMillis() + 1000 * 60));
        String refreshToken = jwtService.generateToken(user, new Date(System.currentTimeMillis() + 1000 * 60 * 30));
        return new TokenWrapper(token, refreshToken);
    }

    public TokenWrapper loginUser(AuthDto authDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        User savedUser = userService.findByUsername(authDto.getUsername());
        return generateTokenWrapper(savedUser);
    }

    public TokenWrapper verify(TokenWrapper tokenWrapper) {

        String username = jwtService.extractUsername(tokenWrapper.getRefreshToken());
        if (username == null)
            throw new RequestNotAuthorized();

        UserDetails userDetails = userService.findByUsername(username);
        if (userDetails == null)
            throw new RequestNotAuthorized();

        if (jwtService.isTokenValid(tokenWrapper.getRefreshToken(), userDetails)) {
            return generateTokenWrapper(userDetails);
        }

        throw new RequestNotAuthorized();
    }
}