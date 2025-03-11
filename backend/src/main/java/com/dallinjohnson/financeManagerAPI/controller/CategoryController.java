package com.dallinjohnson.financeManagerAPI.controller;

import com.dallinjohnson.financeManagerAPI.dto.CategoryDTO;
import com.dallinjohnson.financeManagerAPI.model.Category;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(@AuthenticationPrincipal User user) {
        List<Category> categories = categoryService.findAll(user);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId, @AuthenticationPrincipal User user) {
        Category category = categoryService.findById(categoryId, user);
        return ResponseEntity.ok(category);
    }
    
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, @AuthenticationPrincipal User user) {
        Category category = categoryService.create(categoryDTO, user);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @Valid @RequestBody CategoryDTO categoryDTO,
                                                   @AuthenticationPrincipal User user
    ) {
        Category category = categoryService.update(categoryId, categoryDTO, user);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long categoryId, @AuthenticationPrincipal User user) {
        categoryService.deleteById(categoryId, user);
        return ResponseEntity.noContent().build();
    }
}
