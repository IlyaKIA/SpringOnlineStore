package com.example.store.restController;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.error.ProductNotFoundException;
import com.example.store.error.ProductNotFoundException.ProductErrorResponse;
import com.example.store.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/category")
@AllArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public Category getProductById(@PathVariable Long id) {
        return categoryService.findById(id).orElse(new Category());
    }
    @GetMapping
    public List<Category> getAllProducts(){
        return categoryService.findAllCategory();
    }

    @PostMapping
    public Category addProduct(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @PutMapping
    public Category updateCategory (@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public int deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
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
