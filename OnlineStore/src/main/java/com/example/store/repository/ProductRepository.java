package com.example.store.repository;

import com.example.store.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory_TitleEquals (String categoryTitle, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(Integer minPrise, Integer maxPrise, Pageable pageable);
    Page<Product> findByTitleLikeIgnoreCase (String titleLike,Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndPriceLessThanEqualAndTitleIsLike (Integer minPrise, Integer maxPrise, String titleLike, Pageable pageable);
    Page<Product> findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual (String categoryTitle, Integer minPrise, Integer maxPrise, Pageable pageable);
    Product findFirstByOrderByPrice();
    Product findFirstByOrderByPriceDesc();
//    Page<Product> findProductsByCategory_Title(String categoryTitle);
}