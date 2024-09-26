package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.CartItemException;
import com.xyz.exception.UserException;
import com.xyz.models.CartItem;
import com.xyz.models.User;
import com.xyz.response.ApiResponse;
import com.xyz.service.CartItemService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/cart_items/")
public class CertItemController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
		User user = userService.findUserByJwt(jwt);
		CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
	 
		return new ResponseEntity<CartItem>(updatedCartItem,HttpStatus.OK);
	
	}
	
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> updateCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
		User user = userService.findUserByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
	 
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cartitems Removed Successfully...", true),HttpStatus.OK);
	
	}

}
