package dev.anirban.todo.repo;

import dev.anirban.todo.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckpointRepository extends JpaRepository<Checkpoint, String> {
}
