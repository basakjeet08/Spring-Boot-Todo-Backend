package dev.anirban.todo.service;

import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.CategoryNotFound;
import dev.anirban.todo.exception.UserNotFound;
import dev.anirban.todo.repo.CategoryRepository;
import dev.anirban.todo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;

    public Category create(Category category, String userId) {
        User creator = userRepo
                .findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        Category newCategory = Category
                .builder()
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(creator)
                .build();

        return categoryRepo.save(newCategory);
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Category findById(String id) {
        return categoryRepo
                .findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));
    }

    public void deleteById(String id) {
        if (!categoryRepo.existsById(id))
            throw new CategoryNotFound(id);

        categoryRepo.deleteById(id);
    }
}
