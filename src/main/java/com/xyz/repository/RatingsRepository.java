package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Ratings;


@Repository
public interface RatingsRepository  extends JpaRepository<Ratings, Long>{

	@Query("SELECT r from Ratings r WHERE r.product.id=:productId")
	public List<Ratings> getProductAllRatings(Long productId);
	
}
