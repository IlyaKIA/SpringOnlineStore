package com.example.store.service;

import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    Page<Product> findAllProducts(Pageable pageable);

    Optional<Product> findById(long id);

    Product  addProduct(Product product);

    Page<Product> getProductsFromCategory(String category,  Pageable pageable);

    Page<Product> getProductsFiltered(String category, Integer minPrise, Integer maxPrise, Pageable pageable);

    Integer getMinPrice();
    Integer getMaxPrice();

    Page<Product> getProductsByCharSet(String charSet,  Pageable pageable);

    void deleteProduct(Long id);

    Product saveProductWithPicture(Product product, MultipartFile image);

    Product saveProduct(Product product);
}
