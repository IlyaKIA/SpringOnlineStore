package com.example.store.service;

import com.example.store.domain.Review;

import java.util.List;

public interface ReviewsService {

    List<Review> findAllReview();
    List<Review> findByProductId(long id);
    Review saveReview(Review review);
    void deleteReview(Long id);
}
