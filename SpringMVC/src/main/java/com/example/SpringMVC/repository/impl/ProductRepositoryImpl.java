package com.example.SpringMVC.repository.impl;


import com.example.SpringMVC.Product;
import com.example.SpringMVC.repository.ProductRepository;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private List<Product> products;
    private int id;

    @PostConstruct
    private void init() {
        products = new ArrayList<>();
        String[] product = new String[]{"Bread", "Apple", "Orange", "Potato", "Wine"};
        for (int i = 0; i < 5; i++) {
            products.add(new Product(i, product[i], (i*10+5)));
            id = i;
        }
    }


    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @Override
    public Product addProduct(Product product) {
        id++;
        product.setId(id);
        products.add(product);
        return product;
    }


}
