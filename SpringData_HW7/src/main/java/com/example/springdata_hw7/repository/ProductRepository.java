package com.example.springdata_hw7.repository;

import com.example.springdata_hw7.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_TitleEquals (String categoryTitle);
    List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(Integer minPrise, Integer maxPrise);
    List<Product> findByTitleLike (String titleLike);
    List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqualAndTitleIsLike (Integer minPrise, Integer maxPrise, String titleLike);
    List<Product> findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqualAndTitleLike (String categoryTitle, Integer minPrise, Integer maxPrise, String titleLike);
    Product findFirstByOrderByPrice();
    Product findFirstByOrderByPriceDesc();
}