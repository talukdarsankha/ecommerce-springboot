package com.xyz.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.CreateProductRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Product;
import com.xyz.response.ApiResponse;
import com.xyz.service.ProductService;


@RestController
@RequestMapping("/api/admin/product")
public class AdminProductcontroller {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest){
		Product product = productService.createProduct(createProductRequest);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> createProduct(@PathVariable("productId") Long productId) throws ProductException{
		 productService.deleteProduct(productId);
		 ApiResponse apiResponse = new ApiResponse("Product Deleted Successfully...", true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAlProduct(){
		 List<Product> allProduct = productService.findAllProducts();
 		return new ResponseEntity<List<Product>>(allProduct,HttpStatus.OK);
	}

	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product updateProduct, @PathVariable("productId") Long productId) throws ProductException {
		 Product product = productService.updateProduct(productId, updateProduct);
		
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] createProductRequest) {
	     for(CreateProductRequest product: createProductRequest) {
	    	 productService.createProduct(product);
	     }
	     
	     ApiResponse apiResponse = new ApiResponse("Products Created Successfully...", true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.CREATED);
	}
	

}
