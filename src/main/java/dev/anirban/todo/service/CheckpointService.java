package dev.anirban.todo.service;

import dev.anirban.todo.dto.CheckpointDto;
import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.CheckpointNotFound;
import dev.anirban.todo.exception.RequestNotAuthorized;
import dev.anirban.todo.repo.CheckpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckpointService {

    private final UserService userService;
    private final TodoService todoService;
    private final CheckpointRepository checkpointRepo;

    public Checkpoint create(CheckpointDto checkpoint, User user) {

        User creator = userService.findById(user.getUid());
        Todo parentTodo = todoService.findById(checkpoint.getTodo());

        Checkpoint newCheckpoint = Checkpoint
                .builder()
                .description(checkpoint.getDescription())
                .status(checkpoint.getStatus() != null ? checkpoint.getStatus() : Checkpoint.CheckpointStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        if (!parentTodo.getCreatedBy().getUid().equals(creator.getUid()))
            throw new RequestNotAuthorized();

        creator.addCheckpoint(newCheckpoint);
        parentTodo.addCheckpoint(newCheckpoint);

        return checkpointRepo.save(newCheckpoint);
    }

    public Checkpoint findById(String id) {
        return checkpointRepo
                .findById(id)
                .orElseThrow(() -> new CheckpointNotFound(id));
    }

    public List<Checkpoint> findByCreatedBy_Uid(String createdById) {
        return checkpointRepo.findByCreatedBy_Uid(createdById);
    }

    public List<Checkpoint> findByCreatedBy_UidAndTodo_Id(String createdById, String todoId) {
        return checkpointRepo.findByCreatedBy_UidAndTodo_Id(createdById, todoId);
    }

    public void deleteById(User user, String id) {
        Checkpoint checkpoint = findById(id);
        if (!checkpoint.getCreatedBy().getUid().equals(user.getUid()))
            throw new RequestNotAuthorized();

        checkpointRepo.deleteById(id);
    }
}