package org.example.context.repository.impl;

import org.example.context.domain.Product;
import org.example.context.repository.ProductRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProduct(int id) {
        for (Product p: products) {
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
}
