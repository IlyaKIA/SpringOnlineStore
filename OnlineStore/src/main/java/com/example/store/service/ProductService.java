package com.example.store.service;

import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();

    Optional<Product> findById(long id);
    ProductDTO findByIdDTO(long id);

    Product  addProduct(Product product);

    List<Product> getProductsFromCategory(String category);

    List<Product> getProductsFiltered(String category, Integer minPrise, Integer maxPrise);

    Integer getMinPrice();
    Integer getMaxPrice();

    List<Product> getProductsByCharSet(String charSet);

    void deleteProduct(Long id);

    Product saveProductWithPicture(Product product, MultipartFile image);

    Product saveProduct(Product product);
}
