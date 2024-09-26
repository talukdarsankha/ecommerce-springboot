package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.UseerOrd;


@Repository
public interface OrderRepository extends JpaRepository<UseerOrd, Long> {

	@Query("SELECT o from UseerOrd o WHERE o.user.id=:userId AND o.orderStatus='PLACED' OR o.orderStatus='CONFIRMED' OR o.orderStatus='SHIPPED' OR o.orderStatus='DELIVERED'")          
	public List<UseerOrd> getUserOrders(Long userId);
	
}
