package com.example.blogApp.service;

import java.util.List;


import com.example.blogApp.payload.CategoryDTO;

public interface CategoryService {
	 CategoryDTO addCategory(CategoryDTO CategoryDTO);

	    CategoryDTO getCategory(Long categoryId);

	    List<CategoryDTO> getAllCategories();

	    CategoryDTO updateCategory(CategoryDTO CategoryDTO, Long categoryId);

	    void deleteCategory(Long categoryId);
          
}
