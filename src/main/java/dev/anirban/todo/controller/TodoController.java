package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.dto.TodoDto;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping(UrlConstants.CREATE_TODO)
    public List<TodoDto> create(
            @AuthenticationPrincipal User user,
            @RequestBody TodoDto todo
    ) {
        return service
                .create(todo, user)
                .stream()
                .map(Todo::toTodoDto)
                .toList();
    }

    @GetMapping(UrlConstants.FIND_TODO_BY_ID)
    public TodoDto findById(@PathVariable String id) {
        return service.findById(id).toTodoDto();
    }

    @GetMapping(UrlConstants.FIND_TODO_QUERY)
    public List<TodoDto> findTodoQuery(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "categoryId", required = false) String categoryId
    ) {
        List<Todo> todoList;

        if (categoryId != null)
            todoList = service.findByCreatedBy_UidAndCategory_Id(user.getUid(), categoryId);
        else
            todoList = service.findByCreatedBy_Uid(user.getUid());

        return todoList
                .stream()
                .map(Todo::toTodoDto)
                .toList();
    }

    @DeleteMapping(UrlConstants.DELETE_TODO_BY_ID)
    public List<TodoDto> deleteById(
            @AuthenticationPrincipal User user,
            @PathVariable String id
    ) {
        return service
                .deleteById(user, id)
                .stream()
                .map(Todo::toTodoDto)
                .toList();
    }
}
