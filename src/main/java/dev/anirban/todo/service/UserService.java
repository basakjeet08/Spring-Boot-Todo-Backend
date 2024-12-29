package dev.anirban.todo.service;

import dev.anirban.todo.dto.AuthDto;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public User create(AuthDto authDto) {
        User newUser = User
                .builder()
                .name(authDto.getName())
                .username(authDto.getUsername())
                .password(encoder.encode(authDto.getPassword()))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todosCreated(new HashSet<>())
                .build();

        return userRepo.save(newUser);
    }

    public User findById(String id) {
        return userRepo
                .findById(id)
                .orElseThrow(() -> new UserNotFound(id));
    }

    public User findByUsername(String username) {
        return userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFound(username));
    }

    public void deleteById(String id) {
        if (!userRepo.existsById(id))
            throw new UserNotFound(id);

        userRepo.deleteById(id);
    }
}
