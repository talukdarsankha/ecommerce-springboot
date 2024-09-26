package com.xyz.service;

import com.xyz.Request.AddCartItemRequest;
import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.User;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddCartItemRequest req) throws ProductException, UserException;

	public Cart findCartByUserId(Long userId);
	
}
