package dev.anirban.todo.repo;

import dev.anirban.todo.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckpointRepository extends JpaRepository<Checkpoint, String> {
    List<Checkpoint> findByCreatedBy_Uid(String createdById);

    List<Checkpoint> findByCreatedBy_UidAndTodo_Id(String createdById, String todoId);
}