package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.OrderException;
import com.xyz.models.UseerOrd;
import com.xyz.response.ApiResponse;
import com.xyz.service.OrderService;


@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<UseerOrd>> getAllOrders(){
		List<UseerOrd> allOrders = orderService.getAllOrders();
		return new ResponseEntity<List<UseerOrd>>(allOrders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<UseerOrd> confirmedOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		UseerOrd confirmedOrder = orderService.confirmedOrder(orderId);
		return new ResponseEntity<UseerOrd>(confirmedOrder,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<UseerOrd> shippedOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		UseerOrd shippedOrder = orderService.shippedOrder(orderId);
		return new ResponseEntity<UseerOrd>(shippedOrder,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/placed")
	public ResponseEntity<UseerOrd> placedOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		UseerOrd placedOrder = orderService.placedOrder(orderId);
		return new ResponseEntity<UseerOrd>(placedOrder,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<UseerOrd> deliverOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		UseerOrd deliver = orderService.deliveredOrder(orderId);
		return new ResponseEntity<UseerOrd>(deliver,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<UseerOrd> cancleOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		UseerOrd cancleOrder = orderService.canceledOrder(orderId);
		return new ResponseEntity<UseerOrd>(cancleOrder,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiResponse response = new ApiResponse("order Deleted successfully...", true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}


}
