package dev.anirban.todo.service;

import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.CategoryNotFound;
import dev.anirban.todo.exception.TodoNotFound;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.CategoryRepository;
import dev.anirban.todo.repo.TodoRepository;
import dev.anirban.todo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final TodoRepository todoRepo;

    public Todo create(Todo todo, String userId, String categoryId) {

        Todo newTodo = Todo
                .builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus() != null ? todo.getStatus() : Todo.TodoStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        User creator = userRepo
                .findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));
        creator.addTodo(newTodo);

        if (categoryId != null) {
            Category category = categoryRepo
                    .findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFound(categoryId));
            category.addTodo(newTodo);
        }

        return todoRepo.save(newTodo);
    }

    public List<Todo> findAll() {
        return todoRepo.findAll();
    }

    public Todo findById(String id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFound(id));
    }

    public void deleteById(String id) {
        if (!todoRepo.existsById(id))
            throw new TodoNotFound(id);

        todoRepo.deleteById(id);
    }
}
