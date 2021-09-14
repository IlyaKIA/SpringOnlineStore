package com.example.store.convertor;

import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import com.example.store.service.ProductService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductConvertor {
    public static ProductDTO convertToDTO(Product productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .price(productEntity.getPrice())
                .title(productEntity.getTitle())
                .picturePath(productEntity.getPicturePath())
                .build();
    }

    public static Set<ProductDTO> getAllProductsDTO(List<Product> productEntities) {
        return productEntities.stream()
                .map(ProductConvertor:: convertToDTO)
                .collect(Collectors.toSet());
    }

    public static Product convertToEntity(ProductDTO productDto) {
        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .picturePath(productDto.getPicturePath())
                .build();
    }
}
