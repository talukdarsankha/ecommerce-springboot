package com.xyz.service;

import java.util.List;

import com.xyz.exception.OrderException;
import com.xyz.models.Address;
import com.xyz.models.UseerOrd;
import com.xyz.models.User;

public interface OrderService {
	
	public UseerOrd createOrder(User user,Address shippingAddress);
	
	public UseerOrd findOrderById(Long orderId) throws OrderException;
	
	public UseerOrd placedOrder(Long orderId) throws OrderException;
	
	public List<UseerOrd> getAlluserOrders(Long userId);
	
	public UseerOrd confirmedOrder(Long orderId) throws OrderException;
	
	public UseerOrd shippedOrder(Long orderId) throws OrderException;
	
	public UseerOrd deliveredOrder(Long orderId) throws OrderException;
	
	public UseerOrd canceledOrder(Long orderId) throws OrderException;
	
	public List<UseerOrd> getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
	
	

}
