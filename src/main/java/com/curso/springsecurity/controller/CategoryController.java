package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.SaveCategory;
import com.curso.springsecurity.dto.SaveProduct;
import com.curso.springsecurity.entities.Category;
import com.curso.springsecurity.entities.Product;
import com.curso.springsecurity.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.hasContent()){
            return ResponseEntity.ok(categoryPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()){
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryService.save(saveCategory);
        return  ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("{categotyId}")
    public ResponseEntity<Category> updateProduct (@PathVariable Long categotyId,@RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryService.updateById(categotyId,saveCategory);
        return  ResponseEntity.ok(category);
    }

    @PutMapping("{categoryId}/disabled")
    public ResponseEntity<Category> disableById (@PathVariable Long categoryId){
        Category category = categoryService.disableById(categoryId);
        return  ResponseEntity.status(HttpStatus.OK).body(category);
    }
}
