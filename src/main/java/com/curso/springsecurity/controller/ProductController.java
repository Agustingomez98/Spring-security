package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.SaveProduct;
import com.curso.springsecurity.entities.Product;
import com.curso.springsecurity.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/*@CrossOrigin*/
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable){
        Page<Product> productsPage = productService.findAll(pageable);
        if (productsPage.hasContent()){
            return ResponseEntity.ok(productsPage);
        }
        return ResponseEntity.notFound().build();
    }


    //@CrossOrigin(origins = "https://www.google.com")
    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.save(saveProduct);
        return  ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("{productId}")
    public ResponseEntity<Product> updateProduct (@PathVariable Long productId,@RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.updateProductbyId(productId,saveProduct);
        return  ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("{productId}/disabled")
    public ResponseEntity<Product> disableById (@PathVariable Long productId){
        Product product = productService.disableById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
