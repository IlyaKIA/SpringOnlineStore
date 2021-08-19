package com.example.SpringMVC.service;

import com.example.SpringMVC.domain.Product;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);

    Product  addProduct(Product product);

}
