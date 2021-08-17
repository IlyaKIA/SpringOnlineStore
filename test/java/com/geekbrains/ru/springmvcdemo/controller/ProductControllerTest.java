package com.geekbrains.ru.springmvcdemo.controller;

import com.geekbrains.ru.springmvcdemo.SpringMvcDemoApplicationTest;
import com.geekbrains.ru.springmvcdemo.domain.Product;
import com.geekbrains.ru.springmvcdemo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest extends SpringMvcDemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Product product = new Product(1L, "title", 20);

        Mockito.when(productService.findAll())
                .thenReturn(Collections.singletonList(product));

    }

    @Test
    void getProducts() throws Exception {
        Product product = new Product(1L, "title", 20);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", Collections.singletonList(product)));

    }

}