package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT c from Cart c Where c.user.id=:userId")
	public Cart findCartByUserId(Long userId);
	
}
