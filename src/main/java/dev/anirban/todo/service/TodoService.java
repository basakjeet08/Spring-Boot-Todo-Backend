package dev.anirban.todo.service;

import dev.anirban.todo.dto.TodoDto;
import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.RequestNotAuthorized;
import dev.anirban.todo.exception.TodoNotFound;
import dev.anirban.todo.repo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final TodoRepository todoRepo;

    public List<Todo> create(TodoDto todo, User user) {

        Todo newTodo = Todo
                .builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus() != null ? todo.getStatus() : Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .checkpoints(new HashSet<>())
                .build();

        if (todo.getCheckpoints() != null) {
            todo.getCheckpoints().forEach(checkpointDto ->
                    newTodo.addCheckpoint(Checkpoint
                            .builder()
                            .description(checkpointDto.getDescription())
                            .status(Checkpoint.CheckpointStatus.PENDING)
                            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                            .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                            .build())
            );
        }

        User creator = userService.findById(user.getUid());
        creator.addTodo(newTodo);

        if (todo.getCategory() != null && !todo.getCategory().isBlank()) {
            Category category = categoryService.findById(todo.getCategory());
            if (!category.getCreatedBy().getUid().equals(creator.getUid()))
                throw new RequestNotAuthorized();

            category.addTodo(newTodo);
        }

        todoRepo.save(newTodo);
        return findByCreatedBy_Uid(user.getUid());
    }

    public Todo findById(String id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFound(id));
    }

    public List<Todo> findByCreatedBy_Uid(String userId) {
        return todoRepo.findByCreatedBy_Uid(userId);
    }

    public List<Todo> findByCreatedBy_UidAndCategory_Id(String userId, String categoryId) {
        return todoRepo.findByCreatedBy_UidAndCategory_Id(userId, categoryId);
    }

    public List<Todo> deleteById(User user, String id) {
        Todo todo = findById(id);

        if (!todo.getCreatedBy().getUid().equals(user.getUid()))
            throw new RequestNotAuthorized();

        todoRepo.deleteById(id);

        return findByCreatedBy_Uid(user.getUid());

    }
}
