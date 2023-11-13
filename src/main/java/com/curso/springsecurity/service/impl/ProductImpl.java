package com.curso.springsecurity.service.impl;

import com.curso.springsecurity.dto.SaveProduct;
import com.curso.springsecurity.entities.Category;
import com.curso.springsecurity.entities.Product;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.CategoryRepository;
import com.curso.springsecurity.repositories.ProductRepository;
import com.curso.springsecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductImpl implements ProductService {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(SaveProduct saveProduct) {

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());

        Product product = Product.builder()
                .name(saveProduct.getName())
                .price(saveProduct.getPrice())
                .status(Product.ProductStatus.ENABLED)
                .category(category)
                .build();
         return productRepository.save(product);
    }

    @Override
    public Product updateProductbyId(Long productId, SaveProduct saveProduct) {
        Optional<Category> category = categoryRepository.findById(saveProduct.getCategoryId());
        if (category.isPresent()){
            Product product = productRepository.findById(productId).orElseThrow(()->
                    new NotFoundException("Product not found with id "+productId));
            product.setName(saveProduct.getName());
            product.setPrice(saveProduct.getPrice());
            product.setCategory(category.get());
        }
        return null;
    }

    @Override
    public Product disableById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->
                new NotFoundException("Product not found with id "+productId));
        product.setStatus(Product.ProductStatus.DISABLED);
    return productRepository.save(product);
    }
}
