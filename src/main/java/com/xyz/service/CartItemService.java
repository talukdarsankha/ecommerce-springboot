package com.xyz.service;

import com.xyz.exception.CartItemException;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws UserException, CartItemException;
	
	
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws UserException, CartItemException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
