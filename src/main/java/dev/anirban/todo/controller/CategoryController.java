package dev.anirban.todo.controller;

import dev.anirban.todo.constants.UrlConstants;
import dev.anirban.todo.dto.CategoryDto;
import dev.anirban.todo.entity.Category;
import dev.anirban.todo.entity.User;
import dev.anirban.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping(UrlConstants.CREATE_CATEGORY)
    public CategoryDto create(
            @RequestBody CategoryDto category,
            @AuthenticationPrincipal User user
    ) {
        return service.create(category, user.getUid()).toCategoryDto();
    }

    @GetMapping(UrlConstants.FIND_CATEGORY_BY_USER_ID)
    public List<CategoryDto> findByCreatedBy_Uid(@AuthenticationPrincipal User user) {
        return service
                .findByCreatedBy_Uid(user.getUid())
                .stream()
                .map(Category::toCategoryDto)
                .toList();
    }

    @DeleteMapping(UrlConstants.DELETE_CATEGORY_BY_ID)
    public void deleteById(
            @AuthenticationPrincipal User user,
            @PathVariable String id
    ) {
        service.deleteById(user, id);
    }
}