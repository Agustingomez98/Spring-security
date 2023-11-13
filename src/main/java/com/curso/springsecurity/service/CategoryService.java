package com.curso.springsecurity.service;

import com.curso.springsecurity.dto.SaveCategory;
import com.curso.springsecurity.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long id);


    Category save(SaveCategory saveCategory);

    Category updateById(Long categoryId, SaveCategory saveCategory);

    Category disableById(Long categoryId);
}
