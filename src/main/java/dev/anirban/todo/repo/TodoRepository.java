package dev.anirban.todo.repo;

import dev.anirban.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByCreatedBy_Uid(String userId);
}