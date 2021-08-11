package com.example.SpringMVC.service;

import com.example.SpringMVC.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(long id);

    Product  addProduct(Product product);

}
