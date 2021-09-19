package com.example.store.repository.impl;

import com.example.store.repository.Cart;
import com.example.store.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
//@Scope("prototype")
public class CartImpl implements Cart {


    private List<Product> productsInCart;

    public Optional<Product> findProductByID (Long id) {
        for(Product productFromCart : productsInCart){
            if (Objects.equals(productFromCart.getId(), id)) return Optional.of(productFromCart);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getProducts() {
        return productsInCart;
    }

    @Override
    public void setProduct(Product addingProduct) {
        if (productsInCart == null) productsInCart = new ArrayList<>();
        productsInCart.add(addingProduct);
    }

    @Override
    public void deleteProductFromCart(Long id) {
        productsInCart.remove(findProductByID(id).orElse(new Product()));
    }
}