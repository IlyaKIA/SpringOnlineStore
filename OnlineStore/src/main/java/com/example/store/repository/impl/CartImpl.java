package com.example.store.repository.impl;

import com.example.store.repository.Cart;
import com.example.store.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
//@Scope("prototype")
public class CartImpl implements Cart {


    private List<Product> productsInCart;

    @Override
    public List<Product> getProducts() {
        return productsInCart;
    }

    @Override
    public void setProduct(Product addingProduct) {
        if (productsInCart == null) productsInCart = new ArrayList<>();
        productsInCart.add(addingProduct);
    }
}