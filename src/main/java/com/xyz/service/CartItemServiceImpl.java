package com.xyz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.exception.CartItemException;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.repository.CartItemRepository;
import com.xyz.repository.CartRepository;


@Service
public class CartItemServiceImpl implements CartItemService{
	
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepository.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws UserException, CartItemException {
		// TODO Auto-generated method stub
		
		CartItem prevCartItem = findCartItemById(cartItemId);
		User user = userService.findUserById(userId);
		
		if (user.getId().equals(prevCartItem.getUserId())) {
			  prevCartItem.setQuantity(cartItem.getQuantity());
			  prevCartItem.setPrice(prevCartItem.getProduct().getPrice()*cartItem.getQuantity());
			  prevCartItem.setDiscountedPrice(prevCartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
			  
			  return cartItemRepository.save(prevCartItem);
		}else {

			throw new CartItemException("can't update another user cartItem...");
			  
		}
		
		
		
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		// TODO Auto-generated method stub
		
		return cartItemRepository.isCartItemExist(cart.getId(), product.getId(), size, userId);
		
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws UserException, CartItemException {
		// TODO Auto-generated method stub
		User reqUser = userService.findUserById(userId);
		CartItem cartItem = findCartItemById(cartItemId);
		
		if (reqUser.getId().equals(cartItem.getUserId())) {
			cartItemRepository.delete(cartItem);
		}else {
			throw new CartItemException("you can't remove another user cartItem...");
			
		}
		
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		// TODO Auto-generated method stub
		Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
		if (cartItem.isEmpty()) {
			throw new CartItemException("can't found cartItem...");
		}
		
		return cartItem.get();
	}
	
	

}
