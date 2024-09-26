package com.xyz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Request.ReviewRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Product;
import com.xyz.models.Review;
import com.xyz.models.User;
import com.xyz.repository.ProductRepository;
import com.xyz.repository.RatingsRepository;
import com.xyz.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Review createReview(ReviewRequest request, User user) throws ProductException {
		// TODO Auto-generated method stub
		
		Product product = productService.findProductById(request.getProductId());
		
		Review review = new Review();
		review.setProduct(product);
		review.setUser(user);
		review.setCreatedAt(LocalDateTime.now());
		review.setReview(request.getReview());
		
		Review createReview = reviewRepository.save(review);
		product.getReviews().add(createReview);
		
		productRepository.save(product);
		
		
		return createReview;
	}

	@Override
	public List<Review> getProductAllReview(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.getAllProductReview(productId);
	}

}
