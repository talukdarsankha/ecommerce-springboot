package com.xyz.Request;

import java.util.HashSet;
import java.util.Set;

import com.xyz.models.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
	
	private String title;
	private String description;
	private Integer price;
	private Integer discountedPrice;
	private String discountedPrecent;
	private Integer quantity;
	private String brand;
	private String color;
	private Set<Size> sizes = new HashSet<>();
	private String imageUrl;
	
	private String topLavelCategory;
	private String secondLavelCategory;
	private String thirdLavelCategory;
	

}
