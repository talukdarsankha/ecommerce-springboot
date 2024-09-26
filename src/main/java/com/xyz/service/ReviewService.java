package com.xyz.service;

import java.util.List;

import com.xyz.Request.CreateRatingRequest;
import com.xyz.Request.ReviewRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Ratings;
import com.xyz.models.Review;
import com.xyz.models.User;

public interface ReviewService {
	

    public Review createReview(ReviewRequest request, User user) throws ProductException;
	
	public List<Review> getProductAllReview(Long productId);
	
}
