package dev.anirban.todo.service;

import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.exception.CategoryNotFound;
import dev.anirban.todo.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final UserService userService;

    public Category create(Category category, String userId) {
        User creator = userService.findById(userId);

        Category newCategory = Category
                .builder()
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .todoList(new HashSet<>())
                .build();

        creator.addCategory(newCategory);
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

    public List<Category> findByCreatedBy_Uid(String createdById){
        return categoryRepo.findByCreatedBy_Uid(createdById);
    }

    public void deleteById(String id) {
        if (!categoryRepo.existsById(id))
            throw new CategoryNotFound(id);

        categoryRepo.deleteById(id);
    }
}
