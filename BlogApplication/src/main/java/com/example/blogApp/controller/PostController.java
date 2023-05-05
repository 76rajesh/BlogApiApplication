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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.payload.PostDTO;
import com.example.blogApp.payload.PostResponse;
import com.example.blogApp.service.PostService;
import com.example.blogApp.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	
	private PostService postservice;

	public PostController(PostService postservice) {
		
		this.postservice = postservice;
	}
	
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postdto){
		return new ResponseEntity<>(postservice.createPost(postdto) ,HttpStatus.CREATED);
	}
	
	@GetMapping
	public PostResponse getAllPosts(
	   @RequestParam(value="pageNo" , defaultValue =AppConstants.default_pageno, required =false) int pageNo,
	   @RequestParam(value="pageSize", defaultValue=AppConstants.default_pagesize , required = false) int pageSize,
	   @RequestParam(value ="sortBy" ,defaultValue=AppConstants.default_sortby, required =false) String sortBy,
	   @RequestParam(value="sortDir" ,defaultValue=AppConstants.default_sortdir, required = false) String sortDir){
		return postservice.getAllPost(pageNo,pageSize,sortBy,sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name="id") long id ){
		return ResponseEntity.ok(postservice.getPostById(id));
		
	}
	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto, @PathVariable(name="id") long id ){
	    PostDTO postresponse = 
		postservice.updatePostByID(postdto, id);
	    
	    return new ResponseEntity<>(postresponse,HttpStatus.OK);
		
		
	}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (name="id") long id){
    	
        postservice.deletePostById(id);
    	return new ResponseEntity<>("Post Entity deleted successfully" ,HttpStatus.OK);
    }
    
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDTO> postDtos = postservice.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }

}
