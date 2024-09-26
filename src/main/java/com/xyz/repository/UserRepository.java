package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
	
	public User existsByEmail(String email);
	
	public User findByEmail(String email);

}
