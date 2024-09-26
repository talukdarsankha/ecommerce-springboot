package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.xyz.Config.JwtProvider;
import com.xyz.Request.LoginRequest;
import com.xyz.exception.UserException;
import com.xyz.models.Cart;
import com.xyz.models.User;
import com.xyz.repository.UserRepository;
import com.xyz.response.AuthResponse;
import com.xyz.service.CartService;
import com.xyz.service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws UserException{
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		User existUser = userRepository.findByEmail(email);
		if (existUser!=null) {
			throw new UserException("Email is Already Registered...");
		}
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		createdUser = userRepository.save(createdUser);
		Cart cart = cartService.createCart(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(), createdUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		 String token = jwtProvider.generateToken(authentication);
		
		 AuthResponse authResponse = new AuthResponse(token, "Account Created Successfully...");
		 
		 return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		return new ResponseEntity<AuthResponse>(new AuthResponse(jwt, "Login Successfull..."),HttpStatus.CREATED);
	}

	
	public Authentication authenticate(String email, String password) {
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(email);
		if (userDetails==null) {
			throw new BadCredentialsException("Invalid Username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password"); 
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails , null, userDetails.getAuthorities());
		
		return authentication;
	}
	
}
