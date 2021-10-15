package com.geekbrains.ru.springmvcdemo.service.impl;

import com.geekbrains.ru.springmvcdemo.SpringMvcDemoApplicationTest;
import com.geekbrains.ru.springmvcdemo.domain.Product;
import com.geekbrains.ru.springmvcdemo.repository.ProductRepository;
import com.geekbrains.ru.springmvcdemo.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Optional;

class ProductServiceImplTest extends SpringMvcDemoApplicationTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        productService = new ProductServiceImpl(productRepository);

        Product product = new Product(1L, "title", 20);

        Mockito.when(productRepository.findById(1l))
                .thenReturn(java.util.Optional.of(product));
        Mockito.when(productRepository.findById(5l))
                .thenReturn(java.util.Optional.empty());
        Mockito.when(productService.findAll())
                .thenReturn(Collections.singletonList(product));
    }

    @Test
    void findAll() {
        Product product = new Product(1L, "title", 20);
        Assertions.assertEquals(Collections.singletonList(product), productRepository.findAll());
    }

    @Test
    void findById() {
        Product product = new Product(1L, "title", 20);
        Assertions.assertEquals(Optional.of(product), productRepository.findById(1l));
        Assertions.assertEquals(Optional.empty(), productRepository.findById(5l));
    }
}