package com.xyz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xyz.Request.CreateProductRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Product;

public interface ProductService {
  
	public Product createProduct(CreateProductRequest createProductRequest);
	
	public String deleteProduct(Long id) throws ProductException;
	
	public Product updateProduct(Long productId, Product product) throws ProductException;
	
	public Product findProductById(Long productId) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String Sort, String Stock, Integer pageNumber, Integer pageSize);
	
	public List<Product> findAllProducts();
	
	
	
	
}
