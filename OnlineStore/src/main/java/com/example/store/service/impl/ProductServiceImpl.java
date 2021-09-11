package com.example.store.service.impl;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.repository.ProductRepository;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Patch;
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
    public List<Product> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
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
            if(p.getCategory().getId() == categoryFilter.getId()) {
                productsFilter.add(p);
            }
        }
        return productsFilter;
    }

    @Override
    public List<Product> getProductsFiltered(String category, Integer minPrice, Integer maxPrice) {
        if(category != null && minPrice == null)
            return productRepository.findByCategory_TitleEquals(category);
        else if(minPrice != null && category != null) {
            if(category.equals("All")) return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
            else return productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual(category, minPrice, maxPrice);
        }

//        if(category == null && minPrise != null)
//            return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrise, maxPrise);
//        if(category == null && minPrise != null)
//            return productRepository.findByTitleLike("%" + titleLike + "%");
        return null;
    }

    @Override
    public Integer getMinPrice() {
        return productRepository.findFirstByOrderByPrice().getPrice();
    }

    @Override
    public Integer getMaxPrice() {
        return productRepository.findFirstByOrderByPriceDesc().getPrice();
    }

    @Override
    public List<Product> getProductsByCharSet(String charSet) {
        return productRepository.findByTitleLike("%" + charSet + "%");
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
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
    public Optional<Product> saveProduct(Product product) {
        return Optional.of(productRepository.save(product));
    }

}
