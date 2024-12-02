package dev.anirban.todo.service;

import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User
                .builder()
                .uid("mock uid")
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemail@gmail.com")
                .password("test password 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();
    }


    @Test
    @DisplayName("JUnit test for saveEmployee method")
    public void create_returnsUser() {

        given(userRepo.save(Mockito.any(User.class))).willReturn(user);
        given(passwordEncoder.encode(Mockito.any(String.class))).willReturn("Encoded Password");

        User savedUser = userService.createUser(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUid()).isEqualTo(user.getUid());
    }


    @Test
    @DisplayName("Saving an user with same username throws Exception")
    public void create_withSameUsername_throwsException() {
        given(userRepo.save(Mockito.any())).willThrow(DataIntegrityViolationException.class);
        given(passwordEncoder.encode(Mockito.any(String.class))).willReturn("Encoded Password");

        Assertions.assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(DataIntegrityViolationException.class);
        verify(userRepo, Mockito.atLeastOnce()).save(any(User.class));
    }


    @Test
    @DisplayName("Finding all Users returns List of User")
    public void findAll_returnsUserList() {
        given(userRepo.findAll()).willReturn(List.of(user, user));

        List<User> foundUsers = userService.findAll();

        verify(userRepo, Mockito.atLeastOnce()).findAll();
        Assertions.assertThat(foundUsers).isNotNull();
        Assertions.assertThat(foundUsers).isNotEmpty();
        Assertions.assertThat(foundUsers.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("Finding all users returns empty")
    public void findAll_returnsEmpty() {
        given(userRepo.findAll()).willReturn(List.of());

        List<User> foundUsers = userService.findAll();

        verify(userRepo, atLeastOnce()).findAll();
        Assertions.assertThat(foundUsers).isEmpty();
    }


    @Test
    @DisplayName("Find User by User Id return user")
    public void findById_returnsUser() {
        given(userRepo.findById(user.getUid())).willReturn(Optional.of(user));

        User foundUser = userService.findById(user.getUid());

        verify(userRepo, atLeastOnce()).findById(user.getUid());
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser).isEqualTo(user);
    }


    @Test
    @DisplayName("Find User by User id throws Exception")
    public void findById_throwsException() {
        given(userRepo.findById("Invalid Mock Id")).willReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> userService.findById("Invalid Mock Id"))
                .isInstanceOf(UserNotFound.class);
    }


    @Test
    @DisplayName("Delete user by user id deletes User")
    public void delete_deletesUser() {
        willDoNothing().given(userRepo).deleteById(user.getUid());
        given(userRepo.existsById(user.getUid())).willReturn(true);

        userService.deleteUser(user.getUid());

        verify(userRepo, times(1)).deleteById(user.getUid());
    }

    @Test
    @DisplayName("Delete user by invalid user id throws Exception")
    public void delete_withInvalidId_throwsException() {

        given(userRepo.existsById("Invalid User Id")).willReturn(false);

        Assertions
                .assertThatThrownBy(() -> userService.deleteUser("Invalid User Id"))
                .isInstanceOf(UserNotFound.class);

        verify(userRepo, never()).deleteById("Invalid User Id");
    }
}
