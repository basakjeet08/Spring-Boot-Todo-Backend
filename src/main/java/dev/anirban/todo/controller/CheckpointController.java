package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.service.CheckpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckpointController {

    private final CheckpointService service;

    @PostMapping(UrlConstants.CREATE_CHECKPOINT)
    public Checkpoint create(
            @RequestBody Checkpoint checkpoint,
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "todoId") String todoId
    ) {
        return service.create(checkpoint, userId, todoId);
    }

    @GetMapping(UrlConstants.FIND_ALL_CHECKPOINT)
    public List<Checkpoint> findAll() {
        return service.findAll();
    }

    @GetMapping(UrlConstants.FIND_CHECKPOINT_BY_ID)
    public Checkpoint findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping(UrlConstants.FIND_CHECKPOINT_BY_USER_ID)
    public List<Checkpoint> findByCreatedBy_Uid(@PathVariable String userId) {
        return service.findByCreatedBy_Uid(userId);
    }

    @GetMapping(UrlConstants.FIND_CHECKPOINT_BY_USER_ID_AND_TODO_ID)
    public List<Checkpoint> findByCreatedBy_UidAndTodo_Id(
            @PathVariable String userId, @PathVariable String todoId
    ) {
        return service.findByCreatedBy_UidAndTodo_Id(userId, todoId);
    }

    @DeleteMapping(UrlConstants.DELETE_CHECKPOINT_BY_ID)
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}