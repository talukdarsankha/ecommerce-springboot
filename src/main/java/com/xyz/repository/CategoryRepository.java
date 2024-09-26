package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public Category findByName(String name);
	
	@Query("SELECT c from Category c where c.name=:categoryName AND c.parentCategory.name=:parentCategoryName ")
	public Category findByNameAndParentCategory(String categoryName,String parentCategoryName);
	
}
