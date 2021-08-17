package com.geekbrains.ru.springmvcdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekbrains.ru.springmvcdemo.SpringMvcDemoApplicationTest;
import com.geekbrains.ru.springmvcdemo.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MainControllerTest extends SpringMvcDemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAlpha() throws Exception {
        mockMvc.perform(get("/main/alpha"))
                .andExpect(status().isOk())
                .andExpect(content().string("Alpha"));
    }

    @Test
    void postAlpha() throws Exception {
        Product product = new Product(1L, "title", 20);
        mockMvc.perform(post("/main/alpha")
                        .content(new ObjectMapper().writeValueAsBytes(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(product.toString()));
    }
}