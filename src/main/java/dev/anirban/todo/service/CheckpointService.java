package dev.anirban.todo.service;

import dev.anirban.todo.entity.Checkpoint;
import dev.anirban.todo.entity.Todo;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.CheckpointNotFound;
import dev.anirban.todo.exception.TodoNotFound;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.CheckpointRepository;
import dev.anirban.todo.repo.TodoRepository;
import dev.anirban.todo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckpointService {

    private final UserRepository userRepo;
    private final TodoRepository todoRepo;
    private final CheckpointRepository checkpointRepo;

    public Checkpoint create(Checkpoint checkpoint, String userId, String todoId) {

        User creator = userRepo
                .findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        Todo parentTodo = todoRepo
                .findById(todoId)
                .orElseThrow(() -> new TodoNotFound(todoId));

        Checkpoint newCheckpoint = Checkpoint
                .builder()
                .description(checkpoint.getDescription())
                .status(checkpoint.getStatus() != null ? checkpoint.getStatus() : Checkpoint.CheckpointStatus.PENDING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        creator.addCheckpoint(newCheckpoint);
        parentTodo.addCheckpoint(newCheckpoint);

        return checkpointRepo.save(newCheckpoint);
    }

    public List<Checkpoint> findAll() {
        return checkpointRepo.findAll();
    }

    public Checkpoint findById(String id) {
        return checkpointRepo
                .findById(id)
                .orElseThrow(() -> new CheckpointNotFound(id));
    }

    public void deleteById(String id) {
        if (!checkpointRepo.existsById(id))
            throw new CheckpointNotFound(id);

        checkpointRepo.deleteById(id);
    }
}