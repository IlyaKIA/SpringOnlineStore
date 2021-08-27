package com.example.springdata_hw7.service.impl;

import com.example.springdata_hw7.domain.Category;
import com.example.springdata_hw7.domain.Product;
import com.example.springdata_hw7.repository.CategoryRepository;
import com.example.springdata_hw7.repository.ProductRepository;
import com.example.springdata_hw7.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductsFromCategory(String category) {
        List<Product> products = findAllProducts();
        List<Category> categories = findAllCategory();
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
            if(p.getCategory().getId() == categoryFilter.getId()) {
                productsFilter.add(p);
            }
        }
        return productsFilter;
    }

    @Override
    public List<Product> getProductsFiltered(String category, Integer minPrise, Integer maxPrise, String titleLike) {
        if(category != null && minPrise != null && titleLike != null)
            return productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqualAndTitleLike(category, minPrise, maxPrise, titleLike);
        if(category == null && minPrise != null && titleLike.equals("0"))
            return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrise, maxPrise);
        if(category != null && minPrise == null && titleLike == null)
            return productRepository.findByCategory_TitleEquals(category);
        if(category == null && minPrise != null)
            return productRepository.findByTitleLike("%" + titleLike + "%");
        return null;
    }

    @Override
    public Integer getMinPrise() {
        return productRepository.findFirstByOrderByPrice().getPrice();
    }

    @Override
    public Integer getMaxPrise() {
        return productRepository.findFirstByOrderByPriceDesc().getPrice();
    }

}
