package org.example.context.service;

import org.example.context.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProduct(int id);
}
