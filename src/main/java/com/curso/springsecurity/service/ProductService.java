package com.curso.springsecurity.service;

import com.curso.springsecurity.dto.SaveProduct;
import com.curso.springsecurity.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Product save(SaveProduct saveProduct);

    Product updateProductbyId(Long productId,SaveProduct saveProduct);

    Product disableById(Long productId);
}
