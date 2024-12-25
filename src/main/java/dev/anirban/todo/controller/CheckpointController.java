package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.dto.CheckpointDto;
import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.CheckpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckpointController {

    private final CheckpointService service;

    @PostMapping(UrlConstants.CREATE_CHECKPOINT)
    public CheckpointDto create(
            @AuthenticationPrincipal User user,
            @RequestBody CheckpointDto checkpoint
    ) {
        return service.create(checkpoint, user).toCheckpointDto();
    }

    @GetMapping(UrlConstants.FIND_CHECKPOINT_BY_ID)
    public Checkpoint findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping(UrlConstants.FIND_CHECKPOINT_QUERY)
    public List<CheckpointDto> findCheckpointQuery(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "todoId", required = false) String todoId
    ) {

        List<Checkpoint> checkpointList;
        if (todoId != null)
            checkpointList = service.findByCreatedBy_UidAndTodo_Id(user.getUid(), todoId);
        else
            checkpointList = service.findByCreatedBy_Uid(user.getUid());

        return checkpointList
                .stream()
                .map(Checkpoint::toCheckpointDto)
                .toList();
    }


    @DeleteMapping(UrlConstants.DELETE_CHECKPOINT_BY_ID)
    public void deleteById(
            @AuthenticationPrincipal User user,
            @PathVariable String id
    ) {
        service.deleteById(user, id);
    }
}