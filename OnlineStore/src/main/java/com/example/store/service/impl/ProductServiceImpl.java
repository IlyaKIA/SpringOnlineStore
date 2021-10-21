package com.example.store.service.impl;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.repository.ProductRepository;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.util.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Page<Product> getProductsFromCategory(String category, Pageable pageable) {
        Page<Product> products = findAllProducts(pageable);
        List<Category> categories =  categoryService.findAllCategory();
        Category categoryFilter = null;
        for (Category c : categories) {
            if(c.getTitle().equals(category)){
                categoryFilter = c;
            }
        }
        if(categoryFilter == null){
            return null;
        }
        List<Product> productsFilter = new ArrayList<>();
        for(Product p : products){
            if(p.getCategory().getId().equals(categoryFilter.getId())) {
                productsFilter.add(p);
            }
        }
        return new PageImpl<>(productsFilter, pageable, productsFilter.size());
    }

    @Override
    @Transactional
    public Page<Product> getProductsFiltered(String category, Integer minPrice, Integer maxPrice, Pageable pageable) {
        if(category != null && minPrice == null)
            return productRepository.findByCategory_TitleEquals(category, pageable);
        else if(minPrice != null && category != null) {
            if(category.equals("All")) return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable);
            else return productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual(category, minPrice, maxPrice, pageable);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer getMinPrice() {
        return productRepository.findFirstByOrderByPrice().getPrice();
    }

    @Override
    @Transactional
    public Integer getMaxPrice() {
        return productRepository.findFirstByOrderByPriceDesc().getPrice();
    }

    @Override
    @Transactional
    public Page<Product> getProductsByCharSet(String charSet,  Pageable pageable) {
        return productRepository.findByTitleLikeIgnoreCase("%" + charSet + "%", pageable);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        productRepository.delete(product.orElse(new Product()));
    }

    @Override
    @Transactional
    public Product saveProductWithPicture(Product product, MultipartFile image) {
        Product savedProduct = productRepository.save(product);
        if(image != null && !image.isEmpty()){
            Path imagePath = FileUtils.saveProductImage(image);
            savedProduct.setPicturePath(imagePath.toString());
            productRepository.save(savedProduct);
        }
        return savedProduct;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

}
