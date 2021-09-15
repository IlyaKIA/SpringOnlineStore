package com.example.store.repository;

import com.example.store.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory_TitleEquals (String categoryTitle, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(Integer minPrise, Integer maxPrise, Pageable pageable);
    Page<Product> findByTitleLike (String titleLike,Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndPriceLessThanEqualAndTitleIsLike (Integer minPrise, Integer maxPrise, String titleLike, Pageable pageable);

    @Override
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual (String categoryTitle, Integer minPrise, Integer maxPrise, Pageable pageable);
    Product findFirstByOrderByPrice();
    Product findFirstByOrderByPriceDesc();
}