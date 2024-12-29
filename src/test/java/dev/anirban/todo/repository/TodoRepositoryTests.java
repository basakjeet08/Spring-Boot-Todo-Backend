package dev.anirban.todo.repository;

import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.repo.TodoRepository;
import dev.anirban.todo.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTests {

    @Autowired
    private UserRepository userRepo;
    private User user1, user2;

    @Autowired
    private TodoRepository todoRepo;
    private Todo todo1, todo2;

    @BeforeEach
    public void setupUserAndCategory() {
        user1 = User
                .builder()
                .name("Test User 01")
                .username("Test Username 01")
                .password("test password 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todosCreated(new HashSet<>())
                .build();

        userRepo.save(user1);

        user2 = User
                .builder()
                .name("Test User 02")
                .username("Test Username 02")
                .password("test password 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todosCreated(new HashSet<>())
                .build();

        userRepo.save(user2);
    }

    @BeforeEach
    public void setupTodo() {
        todo1 = Todo
                .builder()
                .description("Description 01")
                .isCompleted(false)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        todo2 = Todo
                .builder()
                .description("Description 02")
                .isCompleted(false)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns List of Todo (positive outcome)")
    public void findByCreatedBy_Id_returnsTodos() {
        user1.addTodo(todo1);
        todoRepo.save(todo1);

        user2.addTodo(todo2);
        todoRepo.save(todo2);

        List<Todo> foundTodo1 = todoRepo.findByCreatedBy_Id(user1.getId());
        List<Todo> foundTodo2 = todoRepo.findByCreatedBy_Id(user2.getId());

        Assertions.assertThat(foundTodo1).isNotNull();
        Assertions.assertThat(foundTodo1.size()).isEqualTo(1);
        Assertions.assertThat(foundTodo1.getFirst()).isEqualTo(todo1);

        Assertions.assertThat(foundTodo2).isNotNull();
        Assertions.assertThat(foundTodo2.size()).isEqualTo(1);
        Assertions.assertThat(foundTodo2.getFirst()).isEqualTo(todo2);
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns Empty (negative outcome)")
    public void findByCreatedBy_Id_returnsEmpty() {
        List<Todo> foundTodos = todoRepo.findByCreatedBy_Id(user1.getId());
        Assertions.assertThat(foundTodos).isEmpty();
    }
}