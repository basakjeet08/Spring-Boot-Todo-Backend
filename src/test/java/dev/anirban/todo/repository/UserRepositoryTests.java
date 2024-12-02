package dev.anirban.todo.repository;

import dev.anirban.todo.entity.User;
import dev.anirban.todo.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepo;

    private User user1, user2;

    @BeforeEach
    public void setupUser() {
        user1 = User
                .builder()
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemail@gmail.com")
                .password("test password 01")
                .avatar("Test Avatar 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        user2 = User
                .builder()
                .name("Test User 02")
                .username("Test Username 02")
                .email("testemail02@gmail.com")
                .password("test password 02")
                .avatar("Test Avatar 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();
    }


    @Test
    @DisplayName("save() -> returns User (positive outcome)")
    public void save_returnsUser() {

        User savedUser = userRepo.save(user1);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUid()).isNotNull();
        Assertions.assertThat(savedUser).isEqualTo(user1);
    }


    @Test
    @DisplayName("save() -> Duplicate username -> throws an Exception (negative outcome)")
    public void save_duplicateUsername_throwsException() {
        user2.setUsername(user1.getUsername());

        userRepo.save(user1);

        Assertions
                .assertThatThrownBy(() -> userRepo.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    @DisplayName("save() -> Duplicate email -> throws an Exception (negative outcome)")
    public void save_duplicateEmail_throwsException() {
        user2.setEmail(user1.getEmail());

        userRepo.save(user1);

        Assertions
                .assertThatThrownBy(() -> userRepo.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    @DisplayName("findAll() -> returns List of User (positive outcome)")
    public void findAll_returnsUsers() {

        userRepo.save(user1);
        userRepo.save(user2);

        List<User> storedUsers = userRepo.findAll();

        Assertions.assertThat(storedUsers).isNotNull();
        Assertions.assertThat(storedUsers.size()).isEqualTo(2);
        Assertions.assertThat(storedUsers.getFirst().getUid()).isEqualTo(user1.getUid());
        Assertions.assertThat(storedUsers.get(1).getUid()).isEqualTo(user2.getUid());
    }


    @Test
    @DisplayName("findAll() -> returns empty List of User (negative outcome)")
    public void findAll_returnsEmpty() {
        List<User> storedUsers = userRepo.findAll();
        Assertions.assertThat(storedUsers).isEmpty();
    }


    @Test
    @DisplayName("findById() -> returns User object (positive outcome)")
    public void findById_returnsUser() {

        User storedUser = userRepo.save(user1);

        Optional<User> foundUser = userRepo.findById(storedUser.getUid());

        Assertions.assertThat(foundUser).isNotEmpty();
        Assertions.assertThat(foundUser.get()).isEqualTo(storedUser);
    }


    @Test
    @DisplayName("findById() -> returns Empty Optional (negative Outcome)")
    public void findById_returnsEmptyOptional() {
        String invalidId = "Invalid Id";
        Optional<User> foundUser = userRepo.findById(invalidId);
        Assertions.assertThat(foundUser).isEmpty();
    }


    @Test
    @DisplayName("deleteById() -> deletes user (positive outcome)")
    public void deleteById_deletesUser() {

        User storedUser = userRepo.save(user1);
        userRepo.deleteById(storedUser.getUid());
        List<User> foundUser = userRepo.findAll();

        Assertions.assertThat(foundUser).isEmpty();
    }
}