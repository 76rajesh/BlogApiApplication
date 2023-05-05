package com.example.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogApp.entity.Category;
import com.example.blogApp.entity.Post;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.payload.PostDTO;
import com.example.blogApp.payload.PostResponse;
import com.example.blogApp.repository.CategoryReposity;
import com.example.blogApp.repository.PostRepository;
import com.example.blogApp.service.PostService;

@Service
public  class PostServiceImple  implements PostService{
   
	private PostRepository postrepo;
	
	private ModelMapper mapper;
	
	private CategoryReposity categoryRepository;
	
	public PostServiceImple(PostRepository postrepo,ModelMapper mapper,
			CategoryReposity categoryRepository) {
		this.postrepo = postrepo;
		this.mapper = mapper;
		this.categoryRepository =categoryRepository;
	}
	public PostDTO createPost(PostDTO postdto) {
		  Category category = categoryRepository.findById(postdto.getCategoryId())
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
	                		postdto.getCategoryId()));

	        // convert DTO to entity
	        Post post = maptoEntity(postdto);
	        post.setCategory(category);
	        Post newpost = postrepo.save(post);

		
		
		//convert to entity to DTO
		PostDTO postresponse = maptoDTO(newpost);	
		return postresponse;
	}
	
	public PostResponse getAllPost(int pageNo,int pageSize, String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
				Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
				
		Pageable page =PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
		  Page<Post>  posts = postrepo.findAll(page);
		  
		  //get content from page object
		  List<Post> listpost = posts.getContent();
		List<PostDTO> content = listpost.stream().
				map(post->maptoDTO(post)).collect(Collectors.toList());
	    PostResponse postresponse = new PostResponse();
	    postresponse.setContent(content);
	    postresponse.setPageNo(posts.getNumber());
	    postresponse.setPageSize(posts.getSize());
	    postresponse.setTotalElements(posts.getTotalElements());
	    postresponse.setTotalPages(posts.getTotalPages());
	    postresponse.setLast(posts.isLast());
	    
	    return postresponse;
	}
	
	private PostDTO maptoDTO(Post post) {
		PostDTO postdto = mapper.map(post, PostDTO.class);
		
//		postdto.setId(post.getId());
//		postdto.setTitle(post.getTitle());
//		postdto.setDescription(post.getDescription());
//		postdto.setContent(post.getContent());
//		
		return postdto;
	}
	private Post maptoEntity(PostDTO postdto) {
		//convert to DTO to entity 
//		Post post = new Post();
//		post.setTitle(postdto.getTitle());
//		post.setDescription(postdto.getDescription());
//		post.setContent(postdto.getContent());
		
		Post post = mapper.map(postdto, Post.class);
			    
	   return post;
	}
	@Override
	public PostDTO getPostById(long id) {
		
		Post post = postrepo.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException ("Post", "id",id));
		
		return maptoDTO(post);
	}
	@Override
	public PostDTO updatePostByID(PostDTO postdto, long id) {
		
		Post post = postrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
   Category category = categoryRepository.findById(postdto.getCategoryId())
                  .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postdto.getCategoryId()));

		post.setTitle(postdto.getTitle());
		post.setDescription(postdto.getDescription());
		post.setContent(postdto.getContent());
		post.setCategory(category);
		Post updatePost = postrepo.save(post);
		
		return maptoDTO(updatePost);
		
		
	}
	
	@Override
	public void deletePostById(long id) {
		Post post = postrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		postrepo.delete(post);
		
	}
	
	 public List<PostDTO> getPostsByCategory(Long categoryId) {

	        Category category = categoryRepository.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postrepo.findByCategoryId(categoryId);

	        return posts.stream().map((post) -> maptoDTO(post))
	                .collect(Collectors.toList());
	    }


	
	

}
