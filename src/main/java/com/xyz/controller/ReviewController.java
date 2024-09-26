package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.CreateRatingRequest;
import com.xyz.Request.ReviewRequest;
import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Ratings;
import com.xyz.models.Review;
import com.xyz.models.User;
import com.xyz.service.RatingsService;
import com.xyz.service.ReviewService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<Review> createRating(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws ProductException, UserException{
		User reUser = userService.findUserByJwt(jwt);
		
		Review review = reviewService.createReview(req, reUser);
		
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> createRating(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String jwt) throws ProductException, UserException{
		User reUser = userService.findUserByJwt(jwt);
		
		List<Review> productReview = reviewService.getProductAllReview(productId);		
		return new ResponseEntity<List<Review>>(productReview,HttpStatus.OK);
	}

}
