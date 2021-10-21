package com.example.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.repository.Cart;
import com.example.store.repository.impl.CartImpl;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {CartServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CartServiceImplTest {
    @MockBean
    private Cart cart;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Test
    void testConstructor() {
        CartImpl cartImpl = new CartImpl();
        assertTrue((new CartServiceImpl(cartImpl)).getProductsFromCart().isEmpty());
        assertNull(cartImpl.getProducts());
    }

    @Test
    void testGetProductsFromCart() {
        when(this.cart.getProducts()).thenReturn(new ArrayList<Product>());
        assertTrue(this.cartServiceImpl.getProductsFromCart().isEmpty());
        verify(this.cart).getProducts();
    }

    @Test
    void testGetProductsFromCart2() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Picture Path");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(0);
        product.setCategory(category);

        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(product);
        when(this.cart.getProducts()).thenReturn(productList);
        assertEquals(1, this.cartServiceImpl.getProductsFromCart().size());
        verify(this.cart).getProducts();
    }

    @Test
    void testSetProductInCart() {
        doNothing().when(this.cart).setProduct((Product) any());

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
        this.cartServiceImpl.setProductInCart(product);
        verify(this.cart).setProduct((Product) any());
        assertTrue(this.cartServiceImpl.getProductsFromCart().isEmpty());
    }

    @Test
    void testCartSum() {
        when(this.cart.getProducts()).thenReturn(new ArrayList<Product>());
        assertEquals(0, this.cartServiceImpl.cartSum().intValue());
        verify(this.cart).getProducts();
        assertTrue(this.cartServiceImpl.getProductsFromCart().isEmpty());
    }

    @Test
    void testDeleteProductFromCart() {
        doNothing().when(this.cart).deleteProductFromCart((Long) any());
        this.cartServiceImpl.deleteProductFromCart(123L);
        verify(this.cart).deleteProductFromCart((Long) any());
        assertTrue(this.cartServiceImpl.getProductsFromCart().isEmpty());
    }
}

