package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping(UrlConstants.CREATE_TODO)
    public Todo create(
            @RequestBody Todo todo,
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "categoryId", required = false) String categoryId
    ) {
        return service.create(todo, userId, categoryId);
    }

    @GetMapping(UrlConstants.FIND_ALL_TODO)
    public List<Todo> findAll() {
        return service.findAll();
    }

    @GetMapping(UrlConstants.FIND_TODO_BY_ID)
    public Todo findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping(UrlConstants.FIND_TODO_BY_USER_ID)
    public List<Todo> findByCreatedBy_Uid(@PathVariable String userId) {
        return service.findByCreatedBy_Uid(userId);
    }

    @GetMapping(UrlConstants.FIND_TODO_BY_USER_ID_AND_CATEGORY_ID)
    public List<Todo> findByCreatedBy_UidAndCategory_Id(
            @PathVariable String userId, @PathVariable String categoryId
    ) {
        return service.findByCreatedBy_UidAndCategory_Id(userId, categoryId);
    }


    @DeleteMapping(UrlConstants.DELETE_TODO_BY_ID)
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
