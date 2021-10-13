package com.example.store.service.impl;

import com.example.store.convertor.ProductConvertor;
import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import com.example.store.repository.ProductRepository;
import com.example.store.service.ProductDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductDtoServiceImpl implements ProductDtoService {
    private final ProductRepository productRepository;

    public ProductDtoServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDTO findByIdDTO(long id) {
        Product productEntity = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ProductConvertor.convertToDTO(productEntity);
    }

    @Override
    @Transactional
    public Set<ProductDTO> getAllProductsDTO() {
        List<Product> productEntities = productRepository.findAll();
        return ProductConvertor.getAllProductsDTO(productEntities);
    }

    @Override
    @Transactional
    public Product saveProduct(ProductDTO productDto) {
        Product productEntity = ProductConvertor.convertToEntity(productDto);
        return productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        productRepository.delete(product.orElse(new Product()));
    }
}
