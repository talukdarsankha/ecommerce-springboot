package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.OrderException;
import com.xyz.exception.UserException;
import com.xyz.models.Address;
import com.xyz.models.UseerOrd;
import com.xyz.models.User;
import com.xyz.service.OrderService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<UseerOrd> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization") String jwt) throws UserException{         
		User user = userService.findUserByJwt(jwt);
		
		UseerOrd useerOrd = orderService.createOrder(user, shippingAddress);
		return new ResponseEntity<UseerOrd>(useerOrd,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user")
	public ResponseEntity<List<UseerOrd>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{         
		User user = userService.findUserByJwt(jwt);
		List<UseerOrd> allOrders = orderService.getAlluserOrders(user.getId());
		return new ResponseEntity<List<UseerOrd>>(allOrders,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<UseerOrd> findOrderById(@PathVariable("orderId") Long orderId) throws OrderException{
		return new ResponseEntity<UseerOrd>(orderService.findOrderById(orderId),HttpStatus.OK);
	}
	
	
	
	

}
