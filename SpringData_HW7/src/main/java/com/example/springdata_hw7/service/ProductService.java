package com.example.springdata_hw7.service;

import com.example.springdata_hw7.domain.Category;
import com.example.springdata_hw7.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();
    List<Category> findAllCategory();

    Optional<Product> findById(long id);

    Product  addProduct(Product product);

    List<Product> getProductsFromCategory(String category);

    List<Product> getProductsFiltered(String category, Integer minPrise, Integer maxPrise, String titleLike);

    Integer getMinPrise();
    Integer getMaxPrise();
}
