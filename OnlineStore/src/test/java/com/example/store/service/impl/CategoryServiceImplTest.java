package com.example.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    void testConstructor() {
        assertTrue((new CategoryServiceImpl(mock(CategoryRepository.class))).findAllCategory().isEmpty());
    }

    @Test
    void testFindAllCategory() {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        when(this.categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> actualFindAllCategoryResult = this.categoryServiceImpl.findAllCategory();
        assertSame(categoryList, actualFindAllCategoryResult);
        assertTrue(actualFindAllCategoryResult.isEmpty());
        verify(this.categoryRepository).findAll();
    }

    @Test
    void testFindById() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");
        Optional<Category> ofResult = Optional.<Category>of(category);
        when(this.categoryRepository.findById((Long) any())).thenReturn(ofResult);
        Optional<Category> actualFindByIdResult = this.categoryServiceImpl.findById(123L);
        assertSame(ofResult, actualFindByIdResult);
        assertTrue(actualFindByIdResult.isPresent());
        verify(this.categoryRepository).findById((Long) any());
        assertTrue(this.categoryServiceImpl.findAllCategory().isEmpty());
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");
        when(this.categoryRepository.save((Category) any())).thenReturn(category);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");
        assertSame(category, this.categoryServiceImpl.saveCategory(category1));
        verify(this.categoryRepository).save((Category) any());
        assertTrue(this.categoryServiceImpl.findAllCategory().isEmpty());
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");
        Optional<Category> ofResult = Optional.<Category>of(category);
        doNothing().when(this.categoryRepository).delete((Category) any());
        when(this.categoryRepository.findById((Long) any())).thenReturn(ofResult);
        this.categoryServiceImpl.deleteCategory(123L);
        verify(this.categoryRepository).delete((Category) any());
        verify(this.categoryRepository).findById((Long) any());
        assertTrue(this.categoryServiceImpl.findAllCategory().isEmpty());
    }
}

