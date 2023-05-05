package com.example.blogApp.service;

import java.util.List;

import com.example.blogApp.payload.PostDTO;
import com.example.blogApp.payload.PostResponse;

public interface PostService {
	PostDTO createPost(PostDTO postdto);
    
	PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(long id);
	
	PostDTO updatePostByID(PostDTO postdto,long id);
	
	void deletePostById(long id);
	
	List<PostDTO> getPostsByCategory(Long categoryId);
	
}
