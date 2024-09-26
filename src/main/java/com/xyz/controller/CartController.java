package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.AddCartItemRequest;
import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.User;
import com.xyz.response.ApiResponse;
import com.xyz.service.CartService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserByJwt(jwt);
		Cart cart = cartService.findCartByUserId(user.getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization") String jwt, @RequestBody AddCartItemRequest addCartItemRequest) throws UserException, ProductException{
		User user = userService.findUserByJwt(jwt);
		 cartService.addCartItem(user.getId(), addCartItemRequest);
		 ApiResponse apiResponse=new ApiResponse("item added to cart...", true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}

}
