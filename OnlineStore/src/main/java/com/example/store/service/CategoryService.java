package com.example.store.service;

import com.example.store.domain.Category;
import com.example.store.domain.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAllCategory();
    Optional<Category> findById(long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
}
