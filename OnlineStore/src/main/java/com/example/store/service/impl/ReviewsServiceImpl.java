package com.example.store.service.impl;

import com.example.store.domain.Product;
import com.example.store.domain.Review;
import com.example.store.repository.ReviewsRepository;
import com.example.store.service.ProductService;
import com.example.store.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final ProductService productService;

    @Override
    public List<Review> findAllReview() {
        return reviewsRepository.findAll();
    }

    @Transactional
    @Override
    public List<Review> findByProductId(long id) {
        return reviewsRepository.findAllByProduct(productService.findById(id).orElse(new Product()));
    }

    @Transactional
    @Override
    public Review saveReview(Review review) {
        return reviewsRepository.save(review);
    }

    @Transactional
    @Override
    public void deleteReview(Long id) {
        reviewsRepository.delete(reviewsRepository.findById(id).orElse(new Review()));
    }
}
