package com.example.store.controller;

import com.example.store.domain.Product;
import com.example.store.error.ProductNotFoundException;
import com.example.store.error.ProductNotFoundException.*;
import com.example.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shop")
@AllArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.findById(id).orElse(new Product());
        return productService.findById(id).orElse(new Product());
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping
    public Optional<Product> addProduct(Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Optional<Product> updateProduct (Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public int deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK.value();
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException exc) {
        ProductErrorResponse productErrorResponse = new ProductErrorResponse();
        productErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        productErrorResponse.setMessage(exc.getMessage());
        productErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(productErrorResponse, HttpStatus.NOT_FOUND);
    }
}
