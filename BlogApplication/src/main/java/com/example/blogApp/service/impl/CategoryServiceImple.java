package com.example.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.blogApp.entity.Category;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.payload.CategoryDTO;
import com.example.blogApp.repository.CategoryReposity;
import com.example.blogApp.service.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService{
	
	private CategoryReposity categoryrepo;
	
	private ModelMapper mapper;
	
	

	public CategoryServiceImple(CategoryReposity categoryrepo, ModelMapper mapper) {
		
		this.categoryrepo =categoryrepo;
		this.mapper = mapper;
	}


    
	public CategoryDTO addCategory(CategoryDTO CategoryDTO) {
		// TODO Auto-generated method stub
		
		Category category = mapper.map(CategoryDTO, Category.class);
		 Category savedCategory = categoryrepo.save(category);
		 
		return mapper.map(savedCategory, CategoryDTO.class);
	}

	
	 public CategoryDTO getCategory(Long categoryId) {

	        Category category = categoryrepo.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

	        return mapper.map(category, CategoryDTO.class);
	    }

	    public List<CategoryDTO> getAllCategories() {

	        List<Category> categories = categoryrepo.findAll();

	        return categories.stream().map((category) -> mapper.map(category, CategoryDTO.class))
	                .collect(Collectors.toList());
	    }

	    public CategoryDTO updateCategory(CategoryDTO CategoryDTO, Long categoryId) {

	        Category category = categoryrepo.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

	        category.setName(CategoryDTO.getName());
	        category.setDescription(CategoryDTO.getDescription());
	        category.setId(categoryId);

	        Category updatedCategory = categoryrepo.save(category);

	        return mapper.map(updatedCategory, CategoryDTO.class);
	    }

	    public void deleteCategory(Long categoryId) {

	        Category category = categoryrepo.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

	        categoryrepo.delete(category);
	    }
}
