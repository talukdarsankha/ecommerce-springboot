package com.xyz.service;

import java.util.List;


import com.xyz.Request.CreateRatingRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Ratings;
import com.xyz.models.User;

public interface RatingsService {
	
	public Ratings createRating(CreateRatingRequest ratingReq, User user) throws ProductException;
	
	public List<Ratings> getProductAllRatings(Long productId);

}
