package dev.anirban.todo.service;

import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFound(username));
    }
}