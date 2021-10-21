package com.example.store.controller;

import com.example.store.StoreApplicationTests;
import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerIntegrationTest extends StoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserProfileService userProfileService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getProducts() throws Exception {
        Pageable pageRequest = PageRequest.of(0, 6);
        Page<Product> pageOfProducts = productService.findAllProducts(pageRequest);
        Integer minPrice = productService.getMinPrice();
        Integer maxPrice = productService.getMaxPrice();
        List<Category> categoryList = categoryService.findAllCategory();

        mockMvc.perform(get("/shop"))
                .andExpect(status().isOk())
                .andExpect(view().name("online_store"))
                .andExpect(model().attribute("pageOfProducts", pageOfProducts))
                .andExpect(model().attribute("minPrice", minPrice))
                .andExpect(model().attribute("maxPrice", maxPrice))
                .andExpect(model().attribute("categoryList", categoryList));
    }
}