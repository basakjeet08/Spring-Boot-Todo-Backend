package dev.anirban.todo.repo;

import dev.anirban.todo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByCreatedBy_Uid(String createdBy);
}