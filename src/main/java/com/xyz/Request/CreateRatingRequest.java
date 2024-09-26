package com.xyz.Request;

import com.xyz.models.Ratings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingRequest {
	
	private Long productId;
	
	private double ratings;

}
