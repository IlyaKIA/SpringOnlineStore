package com.example.store.controller;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(this.productService).deleteProduct((Long) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/shop/delete");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop"));
    }

    @Test
    void testSaveNewProduct() throws Exception {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Picture Path");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(1);
        product.setCategory(category);
        when(this.productService.saveProductWithPicture((Product) any(),
                (org.springframework.web.multipart.MultipartFile) any())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shop/add-or-edit-product");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop"));
    }
}

