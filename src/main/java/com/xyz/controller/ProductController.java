package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.ProductException;
import com.xyz.models.Product;
import com.xyz.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	

	@GetMapping("/")
	public ResponseEntity<Page<Product>> findProductByCategoryHandeler(@RequestParam("category") String category, @RequestParam("color") List<String> color,@RequestParam("size") List<String> size, @RequestParam("minPrice") Integer minPrice,@RequestParam("maxPrice") Integer maxPrice, @RequestParam("minDiscount") Integer minDiscount, @RequestParam("sort") String sort, @RequestParam("stock") String stock, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize){
		
	   Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
	   
	   return new ResponseEntity<Page<Product>>(res,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/id/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) throws ProductException{
	    	return new ResponseEntity<Product>(productService.findProductById(productId),HttpStatus.ACCEPTED);
	}

}
