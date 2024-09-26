package com.xyz.service;

import com.xyz.exception.UserException;
import com.xyz.models.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserByJwt(String jwt) throws UserException;
	

}
