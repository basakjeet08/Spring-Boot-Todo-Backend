package dev.anirban.todo.repository;

import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.repo.CategoryRepository;
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
    private CategoryRepository catRepo;
    private Category category1, category2;

    @Autowired
    private TodoRepository todoRepo;
    private Todo todo1, todo2;

    @BeforeEach
    public void setupUserAndCategory() {
        user1 = User
                .builder()
                .name("Test User 01")
                .username("Test Username 01")
                .email("testemai1l@gmail.com")
                .password("test password 01")
                .roles(User.UserRole.USER)
                .avatar("Test Avatar 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        userRepo.save(user1);

        user2 = User
                .builder()
                .name("Test User 02")
                .username("Test Username 02")
                .email("testemail2@gmail.com")
                .password("test password 02")
                .roles(User.UserRole.USER)
                .avatar("Test Avatar 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoriesCreated(new HashSet<>())
                .todosCreated(new HashSet<>())
                .checkpointCreated(new HashSet<>())
                .build();

        userRepo.save(user2);

        category1 = Category
                .builder()
                .name("Category 01")
                .description("Description 01")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todoList(new HashSet<>())
                .build();
        user1.addCategory(category1);
        catRepo.save(category1);

        category2 = Category
                .builder()
                .name("Category 02")
                .description("Description 02")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todoList(new HashSet<>())
                .build();
        user2.addCategory(category2);
        catRepo.save(category2);
    }

    @BeforeEach
    public void setupTodo() {
        todo1 = Todo
                .builder()
                .title("Title 01")
                .description("Description 01")
                .status(Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .checkpoints(new HashSet<>())
                .build();

        todo2 = Todo
                .builder()
                .title("Title 02")
                .description("Description 02")
                .status(Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .checkpoints(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns List of Todo (positive outcome)")
    public void findByCreatedBy_Uid_returnsTodos() {
        user1.addTodo(todo1);
        todoRepo.save(todo1);

        user2.addTodo(todo2);
        todoRepo.save(todo2);

        List<Todo> foundTodo1 = todoRepo.findByCreatedBy_Uid(user1.getUid());
        List<Todo> foundTodo2 = todoRepo.findByCreatedBy_Uid(user2.getUid());

        Assertions.assertThat(foundTodo1).isNotNull();
        Assertions.assertThat(foundTodo1.size()).isEqualTo(1);
        Assertions.assertThat(foundTodo1.getFirst()).isEqualTo(todo1);

        Assertions.assertThat(foundTodo2).isNotNull();
        Assertions.assertThat(foundTodo2.size()).isEqualTo(1);
        Assertions.assertThat(foundTodo2.getFirst()).isEqualTo(todo2);
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns Empty (negative outcome)")
    public void findByCreatedBy_Uid_returnsEmpty() {
        List<Todo> foundTodos = todoRepo.findByCreatedBy_Uid(user1.getUid());
        Assertions.assertThat(foundTodos).isEmpty();
    }


    @Test
    @DisplayName("findByCreatedBy_UidAndCategory_Id() -> returns Todo List (positive outcome)")
    public void findByCreatedBy_UidAndCategory_Id_returnsTodos() {
        user1.addTodo(todo1);
        category1.addTodo(todo1);
        todoRepo.save(todo1);

        user2.addTodo(todo2);
        category2.addTodo(todo2);
        todoRepo.save(todo2);

        List<Todo> foundTodos1 = todoRepo.findByCreatedBy_UidAndCategory_Id(user1.getUid(), category1.getId());
        List<Todo> foundTodos2 = todoRepo.findByCreatedBy_UidAndCategory_Id(user2.getUid(), category2.getId());

        Assertions.assertThat(foundTodos1).isNotNull();
        Assertions.assertThat(foundTodos1.size()).isEqualTo(1);
        Assertions.assertThat(foundTodos1.getFirst()).isEqualTo(todo1);

        Assertions.assertThat(foundTodos2).isNotNull();
        Assertions.assertThat(foundTodos2.size()).isEqualTo(1);
        Assertions.assertThat(foundTodos2.getFirst()).isEqualTo(todo2);
    }

    @Test
    @DisplayName("findByCreatedBy_UidAndCategory_Id() -> returns Empty (negative outcome)")
    public void findByCreatedBy_UidAndCategory_Id_returnsEmpty() {
        List<Todo> foundTodos = todoRepo.findByCreatedBy_UidAndCategory_Id(user1.getUid(), category1.getId());
        Assertions.assertThat(foundTodos).isEmpty();
    }
}