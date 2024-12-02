package dev.anirban.todo.controller;


import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.entity.Category;
import dev.anirban.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping(UrlConstants.CREATE_CATEGORY)
    public Category create(@RequestBody Category category, @RequestParam(name = "userId") String userId) {
        return service.create(category, userId);
    }

    @GetMapping(UrlConstants.FIND_ALL_CATEGORY)
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping(UrlConstants.FIND_CATEGORY_BY_ID)
    public Category findById(@PathVariable String id) {
        return service.findById(id);
    }

    @DeleteMapping(UrlConstants.DELETE_CATEGORY_BY_ID)
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}