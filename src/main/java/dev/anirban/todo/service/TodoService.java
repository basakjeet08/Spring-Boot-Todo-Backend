package dev.anirban.todo.service;

import dev.anirban.todo.dto.TodoDto;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.RequestNotAuthorized;
import dev.anirban.todo.exception.TodoNotFound;
import dev.anirban.todo.repo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserService userService;
    private final TodoRepository todoRepo;

    public List<Todo> create(TodoDto todo, User user) {

        Todo newTodo = Todo
                .builder()
                .description(todo.getDescription())
                .isCompleted(false)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        User creator = userService.findById(user.getId());
        creator.addTodo(newTodo);

        todoRepo.save(newTodo);
        return findByCreatedBy_Id(user.getId());
    }

    public Todo findById(String id) {
        return todoRepo
                .findById(id)
                .orElseThrow(() -> new TodoNotFound(id));
    }

    public List<Todo> findByCreatedBy_Id(String userId) {
        return todoRepo.findByCreatedBy_Id(userId);
    }

    public List<Todo> deleteById(User user, String id) {
        Todo todo = findById(id);

        if (!todo.getCreatedBy().getId().equals(user.getId()))
            throw new RequestNotAuthorized();

        todoRepo.deleteById(id);

        return findByCreatedBy_Id(user.getId());
    }
}
