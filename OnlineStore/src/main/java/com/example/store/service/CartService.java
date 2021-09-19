package com.example.store.service;

import com.example.store.domain.Product;

import java.util.List;
import java.util.Map;

public interface CartService {
    Map<Product, Long> getProductsFromCart();
    void setProductInCart(Product addingProduct);
    Integer cartSum();

    void deleteProductFromCart(Long id);
}
