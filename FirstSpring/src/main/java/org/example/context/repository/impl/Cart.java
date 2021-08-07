package org.example.context.repository.impl;

import org.example.context.domain.Product;

import java.util.List;

public interface Cart {
    String addProductToCart(Product product);
    String delProductFromCart(int id);
    List<Product> cartList();
}