package com.example.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blogApp.entity.Comment;
import com.example.blogApp.entity.Post;
import com.example.blogApp.exception.BlogApiException;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.payload.CommentDTO;
import com.example.blogApp.repository.CommentRepository;
import com.example.blogApp.repository.PostRepository;
import com.example.blogApp.service.CommentService;


@Service
public class CommentServiceImple implements CommentService{
    
	
	private CommentRepository commentrepo;
	private PostRepository postrepo;
	
	private ModelMapper mapper;
	public CommentServiceImple(CommentRepository commentrepo,PostRepository postrepo,ModelMapper mapper) {
	
		this.commentrepo = commentrepo;
		this.postrepo = postrepo;
		this.mapper = mapper;
	}


	@Override
	public CommentDTO createComment(long postid, CommentDTO commentdto) {
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("Post","id",postid));
		
		Comment comment = maptoEntity(commentdto);
		
		comment.setPost(post);
		
		Comment newComment = commentrepo.save(comment);
		
		
		 
		return maptoDTO(newComment);
	}
    
	
	private CommentDTO maptoDTO(Comment comment) {
//		
//		CommentDTO commentdto = new CommentDTO();
//		commentdto.setId(comment.getId());
//		commentdto.setName(comment.getName());
//		commentdto.setEmail(comment.getEmail());
//		commentdto.setBody(comment.getBody()); 
		
		CommentDTO commentdto = mapper.map(comment, CommentDTO.class);
		
		return commentdto;
	}
	
	private Comment maptoEntity(CommentDTO commentdto) {
//	   Comment	comment = new Comment();
//	   comment.setId(commentdto.getId());
//	   comment.setName(commentdto.getName());
//	   comment.setEmail(commentdto.getEmail());
//	   comment.setBody(commentdto.getBody());
		
		Comment comment = mapper.map(commentdto, Comment.class);
	   
	   return comment;
	}


	@Override
	public List<CommentDTO> getCommentPostById(long postid) {
		// TODO Auto-generated method stub
		
			List<Comment> comments = commentrepo.findByPostId(postid);
			
			
		return comments.stream().map(comment->maptoDTO(comment)).collect(Collectors.toList());
	}


	@Override
	public CommentDTO getCommentById(long postid, long commentid) {
		
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("Post","id",postid));
		 
		Comment comment = commentrepo.findById(commentid).orElseThrow(()->
		new ResourceNotFoundException("Comment", "id",commentid)); 
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post ");
		}
		
		return maptoDTO(comment);
		
	}


	@Override
	public CommentDTO updateComment(long postid, long commentid, CommentDTO commentdto) {
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("Post","id",postid));
		 
		Comment comment = commentrepo.findById(commentid).orElseThrow(()->
		new ResourceNotFoundException("Comment", "id",commentid));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post ");
		}
		
		comment.setName(commentdto.getName());
		comment.setEmail(commentdto.getEmail());
		comment.setBody(commentdto.getBody());
		
		Comment updated = commentrepo.save(comment);
		
		
		return maptoDTO(updated);
	}


	@Override
	public void deleteComment(long postid, long commentid) {
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("Post","id",postid));
		 
		Comment comment = commentrepo.findById(commentid).orElseThrow(()->
		new ResourceNotFoundException("Comment", "id",commentid));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post ");
		}
		
		commentrepo.delete(comment);
	}
}
