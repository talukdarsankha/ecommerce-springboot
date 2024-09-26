package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	@Query("SELECT c from CartItem c where c.cart.id=:cartId and c.product.id=:productId and c.size=:size and c.userId=:userId")
	public CartItem isCartItemExist(Long cartId, Long productId, String size, Long userId);

	
	
}
