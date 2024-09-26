package com.xyz.Request;

import com.xyz.models.Cart;
import com.xyz.models.Product;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
	
	private Long productId;

	private String size;
	
	private int  quantity;
	
	private Integer price;
	

}
