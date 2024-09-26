package com.xyz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Request.CreateRatingRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Product;
import com.xyz.models.Ratings;
import com.xyz.models.User;
import com.xyz.repository.ProductRepository;
import com.xyz.repository.RatingsRepository;

@Service
public class RatingsServiceImpl implements RatingsService {
	
	@Autowired
	private RatingsRepository ratingsRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	

	@Override
	public Ratings createRating(CreateRatingRequest ratingReq, User user) throws ProductException {
		// TODO Auto-generated method stub
		Product product = productService.findProductById(ratingReq.getProductId());
		
		Ratings ratings = new Ratings();
		ratings.setProduct(product);
		ratings.setRating(ratingReq.getRatings());
		ratings.setCreatedAt(LocalDateTime.now());
		ratings.setUser(user);
		
		Ratings savedRating = ratingsRepository.save(ratings);
		
		product.getRatings().add(savedRating);
		productRepository.save(product);
		
		return savedRating;
	}

	@Override
	public List<Ratings> getProductAllRatings(Long productId) {
		// TODO Auto-generated method stub
		return ratingsRepository.getProductAllRatings(productId);
	}

}
