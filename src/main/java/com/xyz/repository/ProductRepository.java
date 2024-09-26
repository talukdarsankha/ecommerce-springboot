package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	
	@Query("SELECT p from Product p WHERE (p.category.name=:category OR :category='') AND ((:minPrice is NULL AND :maxPrice is NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) AND (:minDiscount is NULL OR p.discountedPercent>= :minDiscount) ORDER BY CASE WHEN :Sort ='price_low' THEN p.discountedPrice END ASC,CASE WHEN :Sort = 'price_high' then p.discountedPrice END DESC ")
	public List<Product> filterProducts(String category, Integer minPrice,Integer maxPrice, Integer minDiscount, String Sort);  

}
