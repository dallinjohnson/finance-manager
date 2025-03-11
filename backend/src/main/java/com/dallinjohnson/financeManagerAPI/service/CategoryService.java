package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.CategoryDTO;
import com.dallinjohnson.financeManagerAPI.model.Category;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(User user) {
        return categoryRepository.findAllByUser(user);
    }

    public Category findById(Long id, User user) {
        return categoryRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Transaction category not found with id: " + id));
    }

    @Transactional
    public Category create(CategoryDTO categoryDTO, User user) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        category.setUser(user);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, CategoryDTO categoryDTO, User user) {
        Category category = this.findById(id, user);
        category.setName(categoryDTO.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id, User user) {
        Category category = this.findById(id, user);
        categoryRepository.delete(category);
    }
}
