package com.xyz.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Request.AddCartItemRequest;
import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.repository.CartRepository;


@Service
public class CartServiceImpl  implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	

	@Override
	public Cart createCart(User user) {
		// TODO Auto-generated method stub
		Cart cart = new Cart();
		cart.setUser(user);
		
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddCartItemRequest req) throws ProductException, UserException {
	    Cart cart = cartRepository.findCartByUserId(userId);
	    Product product = productService.findProductById(req.getProductId());

	    // If cart is null, create a new one
	    if (cart == null) {
	        User user = userService.findUserById(userId);
	        cart = createCart(user);
	    }

	    // Check if the cart item already exists
	    CartItem presentCartItem = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

	    if (presentCartItem == null) {
	        CartItem cartItem = new CartItem();
	        cartItem.setUserId(userId);
	        cartItem.setProduct(product);
	        cartItem.setCart(cart); // Make sure to set the cart reference
//	        cartItem.setQuantity(req.getQuantity());
	        cartItem.setSize(req.getSize());
//	        cartItem.setPrice(req.getQuantity() * product.getDiscountedPrice());

	        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
	        cart.getCartItems().add(createdCartItem); 
	        cartRepository.save(cart); 
	    } 

	    return "Item added to cart";
	}


	@Override
	public Cart findCartByUserId(Long userId) {
		// TODO Auto-generated method stub
		Cart cart= cartRepository.findCartByUserId(userId);
		
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;
		
		for(CartItem citm: cart.getCartItems()) {
			totalPrice+=citm.getPrice();
			totalDiscountedPrice+=citm.getDiscountedPrice();
			totalItem+=citm.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(totalItem);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		
		return cartRepository.save(cart);
		
	}
	
	

}
