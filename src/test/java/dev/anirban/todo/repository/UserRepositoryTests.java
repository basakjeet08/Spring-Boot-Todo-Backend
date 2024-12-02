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

    private User newUser1, newUser2;

    @BeforeEach
    public void setupUser() {
        newUser1 = User
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

        newUser2 = User
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
    @DisplayName("Save User will return User")
    public void save_returnsUser() {

        User savedUser = userRepo.save(newUser1);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUid()).isNotNull();
        Assertions.assertThat(savedUser).isEqualTo(newUser1);
    }


    @Test
    @DisplayName("Save User with same username will throw an Exception")
    public void saveWithSameUsername_throwsException() {
        newUser2.setUsername(newUser1.getUsername());

        userRepo.save(newUser1);

        Assertions
                .assertThatThrownBy(() -> userRepo.saveAndFlush(newUser2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    @DisplayName("Save user with same email will throw an Exception")
    public void saveWithSameEmail_throwsException() {
        newUser2.setEmail(newUser1.getEmail());

        userRepo.save(newUser1);

        Assertions
                .assertThatThrownBy(() -> userRepo.saveAndFlush(newUser2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    @DisplayName("Find all will return the list of User")
    public void findAll_returnsUserList() {

        userRepo.save(newUser1);
        userRepo.save(newUser2);

        List<User> storedUsers = userRepo.findAll();

        Assertions.assertThat(storedUsers).isNotNull();
        Assertions.assertThat(storedUsers.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("Find all will return an empty list of User when the table is empty")
    public void findAll_returnsEmpty() {
        List<User> storedUsers = userRepo.findAll();
        Assertions.assertThat(storedUsers).isEmpty();
    }


    @Test
    @DisplayName("Find by Id will return the User object")
    public void findById_returnsUser() {

        User storedUser = userRepo.save(newUser1);

        Optional<User> foundUser = userRepo.findById(storedUser.getUid());

        Assertions.assertThat(foundUser).isNotEmpty();
        Assertions.assertThat(foundUser.get()).isEqualTo(storedUser);
    }


    @Test
    @DisplayName("Find By Id will return Empty when we get nothing with that id")
    public void findById_returnsEmpty() {

        Optional<User> foundUser = userRepo.findById("Invalid Id");
        Assertions.assertThat(foundUser).isEmpty();
    }


    @Test
    @DisplayName("Delete function deletes the user entry")
    public void delete_deletesUser() {

        User storedUser = userRepo.save(newUser1);
        userRepo.deleteById(storedUser.getUid());
        List<User> foundUser = userRepo.findAll();

        Assertions.assertThat(foundUser).isEmpty();
    }
}