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
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    private User user1;

    @BeforeEach
    public void setup() {
        user1 = User
                .builder()
                .id("mock uid 01")
                .name("Test User 01")
                .username("Test Username 01")
                .password("test password 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todosCreated(new HashSet<>())
                .build();
    }


    @Test
    @DisplayName("findById() -> returns User object (positive outcome)")
    public void findById_returnsUser() {
        given(userRepo.findById(user1.getId())).willReturn(Optional.of(user1));

        User foundUser = userService.findById(user1.getId());

        verify(userRepo, times(1)).findById(user1.getId());
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser).isEqualTo(user1);
    }


    @Test
    @DisplayName("findById() -> returns Empty Optional (negative Outcome)")
    public void findById_throwsException() {
        String invalidId = user1.getId();

        given(userRepo.findById(invalidId)).willReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> userService.findById(invalidId))
                .isInstanceOf(UserNotFound.class);

        verify(userRepo, times(1)).findById(invalidId);
    }


    @Test
    @DisplayName("deleteById() -> deletes user (positive outcome)")
    public void deleteById_deletesUser() {
        willDoNothing().given(userRepo).deleteById(user1.getId());
        given(userRepo.existsById(user1.getId())).willReturn(true);

        userService.deleteById(user1.getId());

        verify(userRepo, times(1)).deleteById(user1.getId());
    }

    @Test
    @DisplayName("deleteById() -> throws Exception (negative outcome)")
    public void deleteById_withInvalidId_throwsException() {

        String invalidId = user1.getId();
        given(userRepo.existsById(invalidId)).willReturn(false);

        Assertions
                .assertThatThrownBy(() -> userService.deleteById(invalidId))
                .isInstanceOf(UserNotFound.class);

        verify(userRepo, never()).deleteById(invalidId);
    }
}
