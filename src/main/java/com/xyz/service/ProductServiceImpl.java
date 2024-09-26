package com.xyz.service;

import java.lang.StackWalker.Option;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyz.Request.CreateProductRequest;
import com.xyz.exception.ProductException;
import com.xyz.models.Category;
import com.xyz.models.Product;
import com.xyz.repository.CategoryRepository;
import com.xyz.repository.ProductRepository;
import com.xyz.repository.UserRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Product createProduct(CreateProductRequest createProductRequest) {
		// TODO Auto-generated method stub
		
		Category topCategory = categoryRepository.findByName(createProductRequest.getTopLavelCategory());
		if (topCategory ==null) {
			Category topLavelCategory = new Category();
			topLavelCategory.setName(createProductRequest.getTopLavelCategory());
			topLavelCategory.setLavel(1);
			
			topCategory =categoryRepository.save(topLavelCategory);
		}
		
		Category secondCategory = categoryRepository.findByNameAndParentCategory(createProductRequest.getSecondLavelCategory(), createProductRequest.getTopLavelCategory());
		if (secondCategory==null) {
			Category secondLavelCategoy = new Category();
			secondLavelCategoy.setName(createProductRequest.getSecondLavelCategory());
			secondLavelCategoy.setParentCategory(topCategory);
             secondLavelCategoy.setLavel(2);
             
             secondCategory= categoryRepository.save(secondLavelCategoy);
		}
		
		Category thirdCategory = categoryRepository.findByNameAndParentCategory(createProductRequest.getThirdLavelCategory(), createProductRequest.getSecondLavelCategory());
		if (thirdCategory==null) {
			Category thirdLavelCategoy = new Category();
			thirdLavelCategoy.setName(createProductRequest.getThirdLavelCategory());
			thirdLavelCategoy.setParentCategory(secondCategory);
			thirdLavelCategoy.setLavel(3);
			
			thirdCategory = categoryRepository.save(thirdLavelCategoy);
		}
		
		Product product = new Product();
		product.setTitle(createProductRequest.getTitle());
		product.setDescription(createProductRequest.getDescription());
		product.setColor(createProductRequest.getColor());
		product.setBrand(createProductRequest.getBrand());
		product.setDiscountedPercent(createProductRequest.getDiscountedPrecent());
		product.setDiscountedPrice(createProductRequest.getDiscountedPrice());
		product.setImgUrl(createProductRequest.getImageUrl());
		product.setPrice(createProductRequest.getPrice());
		product.setSizes(createProductRequest.getSizes());
		product.setQuantity(createProductRequest.getQuantity());
		product.setCategory(thirdCategory);
		product.setCreatedAt(LocalDateTime.now());
		
		return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Long id) throws ProductException {
		// TODO Auto-generated method stub
		Product product = findProductById(id);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product Deleted Successfully...";
	}

	@Override
	public Product updateProduct(Long productId, Product product) throws ProductException {
		// TODO Auto-generated method stub
		Product prevProduct = findProductById(productId);
		if (product.getQuantity()!=0 || product.getQuantity()!=null ) {
			prevProduct.setQuantity(product.getQuantity());
		}
		return productRepository.save(prevProduct);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		// TODO Auto-generated method stub
		Optional<Product> opt = productRepository.findById(productId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product Not Found With This Id :"+productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String Sort, String Stock, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
       
		Pageable pageable= PageRequest.of(pageNumber, pageSize);
		List<Product> filterProducts= productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, Sort);
		
		if (!colors.isEmpty()) {
			filterProducts= filterProducts.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
		}
		
		if (Stock!=null) {
			if (Stock.equalsIgnoreCase("in_stock")) {
				filterProducts = filterProducts.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}else if (Stock.equalsIgnoreCase("out_stock")) {
				filterProducts = filterProducts.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		
		int startIndex =(int) pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(), filterProducts.size());
		
		List<Product> pageContent = filterProducts.subList(startIndex, endIndex);
		Page<Product> pageProduct = new PageImpl<>(pageContent,pageable, filterProducts.size());
		
		return pageProduct;
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}
	
	
	

}
