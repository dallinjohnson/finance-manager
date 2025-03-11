package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.CategoryDTO;
import com.dallinjohnson.financeManagerAPI.model.Category;
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

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction category not found with id: " + id));
    }

    @Transactional
    public Category create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, CategoryDTO categoryDTO) {
        Category category = this.findById(id);
        category.setName(categoryDTO.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        Category category = this.findById(id);
        categoryRepository.delete(category);
    }
}
