package com.xyz.controller;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.xyz.exception.OrderException;
import com.xyz.models.PaymentDetails;
import com.xyz.models.UseerOrd;
import com.xyz.repository.OrderRepository;
import com.xyz.response.ApiResponse;
import com.xyz.response.PaymentLinkResponse;
import com.xyz.service.OrderService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentControlleer {
	
	
	@Value("${razorpay.api.key}") String apiKey;
	
	@Value("${razorpay.api.secret}") String apiSecret;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException{     
		UseerOrd order = orderService.findOrderById(orderId);
		
		try {
			
			RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
			
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalDiscountedPrice()*100);
			paymentLinkRequest.put("currency", "INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name", order.getUser().getFirstName());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/"+orderId);
			paymentLinkRequest.put("callback_method", "get");
			
			PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");
			
			PaymentLinkResponse res = new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RazorpayException(e.getMessage());
		}
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam("payment_id") String paymentId, @RequestParam("orderId") Long orderId) throws RazorpayException {
		UseerOrd order = new UseerOrd();
		RazorpayClient razorPay = new RazorpayClient(apiKey, apiSecret);
		
		try {
			Payment payment = razorPay.payments.fetch(paymentId);
			if (payment.get("status").equals("captured")) {
				PaymentDetails paymentDetails = new PaymentDetails();
				 paymentDetails.setStatus("COMPLETED");
				 
				order.setOrderStatus("PLACED");
				order.setPaymentDetails(paymentDetails);
				orderRepository.save(order);
			}
			
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setMessage("your order get placed");
			apiResponse.setStatus(true);
			
			return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RazorpayException(e.getMessage());
		}
		
	}
	
}
