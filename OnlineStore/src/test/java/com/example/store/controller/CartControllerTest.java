package com.example.store.controller;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.service.CartService;
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
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    void testDeleteProductFromCartPost() throws Exception {
        doNothing().when(this.cartService).deleteProductFromCart((Long) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/shop/cart/delete");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop/cart"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop/cart"));
    }

    @Test
    void testGetProducts() throws Exception {
        when(this.cartService.getProductsFromCart()).thenReturn(new HashMap<Product, Long>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shop/cart");
        MockMvcBuilders.standaloneSetup(this.cartController)
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
        Optional<Product> ofResult = Optional.<Product>of(product);
        when(this.productService.findById(anyLong())).thenReturn(ofResult);
        doNothing().when(this.cartService).setProductInCart((Product) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/shop/cart/add-product-to-cart");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop"));
    }
}

