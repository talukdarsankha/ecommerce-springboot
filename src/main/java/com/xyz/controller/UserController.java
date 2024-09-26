package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.ProductException;
import com.xyz.exception.UserException;
import com.xyz.models.Review;
import com.xyz.models.User;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUser( @RequestHeader("Authorization") String jwt ) throws UserException{
		User reUser = userService.findUserByJwt(jwt);
			
		return new ResponseEntity<User>(reUser,HttpStatus.OK);
	}

}
