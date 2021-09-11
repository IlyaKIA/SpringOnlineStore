package com.example.store.repository;

import com.example.store.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface Cart {
    List<Product> getProducts();
    void setProduct(Product addingProduct);
}
