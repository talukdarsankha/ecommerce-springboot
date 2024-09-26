
package com.xyz.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.exception.OrderException;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.OrderItem;
import com.xyz.models.PaymentDetails;
import com.xyz.models.UseerOrd;
import com.xyz.models.User;
import com.xyz.repository.AddressRepository;
import com.xyz.repository.OrderItemRepository;
import com.xyz.repository.OrderRepository;
import com.xyz.repository.UserRepository;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public UseerOrd createOrder(User user, Address shippingAddress) {
		// TODO Auto-generated method stub
		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem e: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(e.getPrice());
			orderItem.setQuantity(e.getQuantity());
			orderItem.setProduct(e.getProduct());
			orderItem.setSize(e.getSize());
			
			
			
			orderItem.setUserId(e.getUserId());
			orderItem.setDiscountedPrice(e.getDiscountedPrice());
		 
			OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		UseerOrd order = new UseerOrd();
		order.setUser(user);
		order.setOrderItems(orderItems);
		order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		order.setTotalPrice(cart.getTotalPrice());
		order.setDiscount(cart.getDiscount());
		order.setTortalItem(cart.getTotalItem());
		
		order.setShippingAddress(address);
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus("PENDING");
		order.setCreatedAt(LocalDateTime.now());
//		order.getPaymentDetails().setStatus("PENDING");
		
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setStatus("PENDING");
		order.setPaymentDetails(paymentDetails);
		
		UseerOrd createdOrder = orderRepository.save(order);
		
		for(OrderItem e: orderItems) {
			e.setUseerOrd(createdOrder);
			orderItemRepository.save(e);
		} 
		
		
		return createdOrder;
	}

	@Override
	public UseerOrd findOrderById(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		Optional<UseerOrd> opt = orderRepository.findById(orderId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not found with this id :"+orderId);
	}

	@Override
	public UseerOrd placedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		UseerOrd order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return orderRepository.save(order);
	}

	@Override
	public List<UseerOrd> getAlluserOrders(Long userId) {
		// TODO Auto-generated method stub
		return orderRepository.getUserOrders(userId);
	}

	@Override
	public UseerOrd confirmedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		
		UseerOrd order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepository.save(order);
	}

	@Override
	public UseerOrd shippedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		UseerOrd order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		
		return orderRepository.save(order);
	}

	@Override
	public UseerOrd deliveredOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		UseerOrd order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		
		return orderRepository.save(order);
	}

	@Override
	public UseerOrd canceledOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		UseerOrd order = findOrderById(orderId);
		order.setOrderStatus("CANCELED");
		
		return orderRepository.save(order);
	}

	@Override
	public List<UseerOrd> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		UseerOrd order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}

}
