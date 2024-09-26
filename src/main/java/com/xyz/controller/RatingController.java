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
import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Ratings;
import com.xyz.models.User;
import com.xyz.service.ProductService;
import com.xyz.service.RatingsService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingsService ratingsService;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<Ratings> createRating(@RequestBody CreateRatingRequest req, @RequestHeader("Authorization") String jwt) throws ProductException, UserException{
		User reUser = userService.findUserByJwt(jwt);
		
		Ratings ratings = ratingsService.createRating(req, reUser);
		
		return new ResponseEntity<Ratings>(ratings,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Ratings>> createRating(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String jwt) throws ProductException, UserException{
		User reUser = userService.findUserByJwt(jwt);
		
		List<Ratings> productRatings = ratingsService.getProductAllRatings(productId);		
		return new ResponseEntity<List<Ratings>>(productRatings,HttpStatus.OK);
	}

}
