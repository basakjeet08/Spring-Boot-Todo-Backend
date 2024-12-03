package dev.anirban.todo.repository;

import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.repo.CheckpointRepository;
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
public class CheckpointRepositoryTests {
    @Autowired
    private UserRepository userRepo;
    private User user1, user2;

    @Autowired
    private TodoRepository todoRepo;
    private Todo todo1, todo2;

    @Autowired
    private CheckpointRepository checkRepo;
    private Checkpoint checkpoint1, checkpoint2;

    @BeforeEach
    public void setupUser_CategoryAndTodo() {
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

        todo1 = Todo
                .builder()
                .title("Title 01")
                .description("Description 01")
                .status(Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .checkpoints(new HashSet<>())
                .build();
        user1.addTodo(todo1);
        todoRepo.save(todo1);

        todo2 = Todo
                .builder()
                .title("Title 02")
                .description("Description 02")
                .status(Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .checkpoints(new HashSet<>())
                .build();
        user2.addTodo(todo2);
        todoRepo.save(todo2);
    }

    @BeforeEach
    public void setupCheckpoint() {
        checkpoint1 = Checkpoint
                .builder()
                .description("Description 01")
                .status(Checkpoint.CheckpointStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        checkpoint2 = Checkpoint
                .builder()
                .description("Description 02")
                .status(Checkpoint.CheckpointStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }


    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns List of Checkpoint (positive outcome)")
    public void findByCreatedBy_Uid_returnsCheckpoints() {
        user1.addCheckpoint(checkpoint1);
        todo1.addCheckpoint(checkpoint1);
        checkRepo.save(checkpoint1);

        user2.addCheckpoint(checkpoint2);
        todo2.addCheckpoint(checkpoint2);
        checkRepo.save(checkpoint2);

        List<Checkpoint> foundCheckpoints1 = checkRepo.findByCreatedBy_Uid(user1.getUid());
        List<Checkpoint> foundCheckpoints2 = checkRepo.findByCreatedBy_Uid(user2.getUid());


        Assertions.assertThat(foundCheckpoints1).isNotNull();
        Assertions.assertThat(foundCheckpoints1.size()).isEqualTo(1);
        Assertions.assertThat(foundCheckpoints1.getFirst()).isEqualTo(checkpoint1);

        Assertions.assertThat(foundCheckpoints2).isNotNull();
        Assertions.assertThat(foundCheckpoints2.size()).isEqualTo(1);
        Assertions.assertThat(foundCheckpoints2.getFirst()).isEqualTo(checkpoint2);
    }

    @Test
    @DisplayName("findByCreatedBy_Uid() -> returns Empty (negative outcome)")
    public void findByCreatedBy_Uid_returnsEmpty() {
        List<Checkpoint> foundCheckpoints = checkRepo.findByCreatedBy_Uid(user1.getUid());
        Assertions.assertThat(foundCheckpoints).isEmpty();
    }

    @Test
    @DisplayName("findByCreatedBy_UidAndTodo_Id() -> returns List of Checkpoint (positive outcome)")
    public void findByCreatedBy_UidAndTodo_Id_returnsCheckpoints() {
        user1.addCheckpoint(checkpoint1);
        todo1.addCheckpoint(checkpoint1);
        checkRepo.save(checkpoint1);

        user2.addCheckpoint(checkpoint2);
        todo2.addCheckpoint(checkpoint2);
        checkRepo.save(checkpoint2);

        List<Checkpoint> foundCheckpoints1 = checkRepo.findByCreatedBy_UidAndTodo_Id(user1.getUid(), todo1.getId());
        List<Checkpoint> foundCheckpoints2 = checkRepo.findByCreatedBy_UidAndTodo_Id(user2.getUid(), todo2.getId());


        Assertions.assertThat(foundCheckpoints1).isNotNull();
        Assertions.assertThat(foundCheckpoints1.size()).isEqualTo(1);
        Assertions.assertThat(foundCheckpoints1.getFirst()).isEqualTo(checkpoint1);

        Assertions.assertThat(foundCheckpoints2).isNotNull();
        Assertions.assertThat(foundCheckpoints2.size()).isEqualTo(1);
        Assertions.assertThat(foundCheckpoints2.getFirst()).isEqualTo(checkpoint2);
    }

    @Test
    @DisplayName("findByCreatedBy_UidAndTodo_Id() -> returns Empty (negative outcome)")
    public void findByCreatedBy_UidAndTodo_Id_returnsEmpty() {
        List<Checkpoint> foundCheckpoints = checkRepo.findByCreatedBy_UidAndTodo_Id(user1.getUid(), todo1.getId());
        Assertions.assertThat(foundCheckpoints).isEmpty();
    }
}
