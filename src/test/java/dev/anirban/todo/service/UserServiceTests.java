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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    private User user1, user2;

    @BeforeEach
    public void setup() {
        user1 = User
                .builder()
                .uid("mock uid 01")
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemail01@gmail.com")
                .password("test password 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        user2 = User
                .builder()
                .uid("mock uid 02")
                .name("Test User 02")
                .username("Test Username 02")
                .email("testemail02@gmail.com")
                .password("test password 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("findAll() -> returns List of User (positive outcome)")
    public void findAll_returnsUsers() {
        given(userRepo.findAll()).willReturn(List.of(user1, user2));

        List<User> foundUsers = userService.findAll();

        verify(userRepo, Mockito.times(1)).findAll();
        Assertions.assertThat(foundUsers).isNotNull();
        Assertions.assertThat(foundUsers).isNotEmpty();
        Assertions.assertThat(foundUsers.size()).isEqualTo(2);
        Assertions.assertThat(foundUsers.getFirst().getUid()).isEqualTo(user1.getUid());
        Assertions.assertThat(foundUsers.get(1).getUid()).isEqualTo(user2.getUid());
    }


    @Test
    @DisplayName("findAll() -> returns empty List of User (negative outcome)")
    public void findAll_returnsEmpty() {
        given(userRepo.findAll()).willReturn(List.of());

        List<User> foundUsers = userService.findAll();

        verify(userRepo, times(1)).findAll();
        Assertions.assertThat(foundUsers).isEmpty();
    }


    @Test
    @DisplayName("findById() -> returns User object (positive outcome)")
    public void findById_returnsUser() {
        given(userRepo.findById(user1.getUid())).willReturn(Optional.of(user1));

        User foundUser = userService.findById(user1.getUid());

        verify(userRepo, times(1)).findById(user1.getUid());
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser).isEqualTo(user1);
    }


    @Test
    @DisplayName("findById() -> returns Empty Optional (negative Outcome)")
    public void findById_throwsException() {
        String invalidId = user1.getUid();

        given(userRepo.findById(invalidId)).willReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> userService.findById(invalidId))
                .isInstanceOf(UserNotFound.class);

        verify(userRepo, times(1)).findById(invalidId);
    }


    @Test
    @DisplayName("deleteById() -> deletes user (positive outcome)")
    public void deleteById_deletesUser() {
        willDoNothing().given(userRepo).deleteById(user1.getUid());
        given(userRepo.existsById(user1.getUid())).willReturn(true);

        userService.deleteById(user1.getUid());

        verify(userRepo, times(1)).deleteById(user1.getUid());
    }

    @Test
    @DisplayName("deleteById() -> throws Exception (negative outcome)")
    public void deleteById_withInvalidId_throwsException() {

        String invalidId = user1.getUid();
        given(userRepo.existsById(invalidId)).willReturn(false);

        Assertions
                .assertThatThrownBy(() -> userService.deleteById(invalidId))
                .isInstanceOf(UserNotFound.class);

        verify(userRepo, never()).deleteById(invalidId);
    }
}
