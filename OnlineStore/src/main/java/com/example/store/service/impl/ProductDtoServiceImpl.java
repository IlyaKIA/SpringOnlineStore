package com.example.store.service.impl;

import com.example.store.convertor.ProductConvertor;
import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import com.example.store.repository.ProductRepository;
import com.example.store.service.ProductDtoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class ProductDtoServiceImpl implements ProductDtoService {
    private final ProductRepository productRepository;

    public ProductDtoServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO findByIdDTO(long id) {
        Product productEntity = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ProductConvertor.convertToDTO(productEntity);
    }

    @Override
    public Set<ProductDTO> getAllProductsDTO() {
        List<Product> productEntities = productRepository.findAll();
        return ProductConvertor.getAllProductsDTO(productEntities);
    }

    @Override
    public Product saveProduct(ProductDTO productDto) {
        Product productEntity = ProductConvertor.convertToEntity(productDto);
        return productRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
    }
}
