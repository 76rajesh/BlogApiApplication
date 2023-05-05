package com.example.blogApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.payload.CategoryDTO;
import com.example.blogApp.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Build Add Category REST API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO CategoryDTO){
        CategoryDTO savedCategory = categoryService.addCategory(CategoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Build Get Category REST API
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long categoryId){
         CategoryDTO CategoryDTO = categoryService.getCategory(categoryId);
         return ResponseEntity.ok(CategoryDTO);
    }

    // Build Get All Categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

   
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO CategoryDTO,
                                                      @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(CategoryDTO, categoryId));
    }

    // Build Delete Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!.");
    }
}