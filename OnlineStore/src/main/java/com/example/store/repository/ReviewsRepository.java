package com.example.store.repository;

import com.example.store.domain.Product;
import com.example.store.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct(Product product);
    Review save(Review review);
}
