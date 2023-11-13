package com.curso.springsecurity.service.impl;

import com.curso.springsecurity.dto.SaveCategory;
import com.curso.springsecurity.entities.Category;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.CategoryRepository;
import com.curso.springsecurity.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(SaveCategory saveCategory) {

        Category category = Category.builder()
                .name(saveCategory.getName())
                .status(Category.CategoryStatus.ENABLED)
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public Category updateById(Long categoryId, SaveCategory saveCategory) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new NotFoundException("Category not found with id "+categoryId));

        category.setName(saveCategory.getName());

        return category;
    }

    @Override
    public Category disableById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new NotFoundException("Category not found with id "+categoryId));
        category.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(category);
    }
}
