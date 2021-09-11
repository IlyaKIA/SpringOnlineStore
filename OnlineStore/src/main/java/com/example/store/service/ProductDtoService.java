package com.example.store.service;

import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import java.util.Set;

public interface ProductDtoService {

    ProductDTO findByIdDTO(long id);
    Set<ProductDTO> getAllProductsDTO();

    Product saveProduct(ProductDTO productDto);

    void deleteProduct(Long id);
}
