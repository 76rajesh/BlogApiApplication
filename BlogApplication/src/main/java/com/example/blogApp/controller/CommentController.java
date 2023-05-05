package com.example.blogApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.payload.CommentDTO;
import com.example.blogApp.service.CommentService;

import jakarta.validation.Valid;

@RequestMapping("api/posts")
@RestController
public class CommentController {
  
	private CommentService commentservice;
	
	public CommentController(CommentService commentservice) {
	
		this.commentservice = commentservice;
	}
	
	@PostMapping("/{postid}/comments")
	public ResponseEntity<CommentDTO> createcomment(@PathVariable (value="postid")long postid, 
			@Valid @RequestBody CommentDTO commentdto){
		
		return new ResponseEntity<>(commentservice.createComment(postid, commentdto),HttpStatus.CREATED);
		
	}
	             
	@GetMapping("/{postid}/comments")
	public List<CommentDTO> getCommentByPostId(@PathVariable (value="postid") long postid){
		return commentservice.getCommentPostById(postid);
	}
	
    @GetMapping("/{postid}/comments/{commentid}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value ="postid") long postid ,
	                                 @PathVariable(value ="commentid") long commentid) {
		
	      CommentDTO commentdto = commentservice.getCommentById(postid, commentid);
	      return new ResponseEntity<>(commentdto,HttpStatus.OK);
	      
    
    }
    
    @PutMapping("/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable (value="postid") long postid,
    		@PathVariable (value="commentid") long commentid,
    	   @Valid @RequestBody	CommentDTO commentdto ){
                   CommentDTO commentdtoupdate = commentservice.updateComment(postid, commentid, commentdto);   	        
    	return new ResponseEntity<>(commentdtoupdate,HttpStatus.OK);
    }
	
    
    @DeleteMapping("/{postid}/comments/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable (value="postid") long postid,
    		@PathVariable (value="commentid") long commentid){
    	 commentservice.deleteComment(postid, commentid);
    	return new ResponseEntity<>("Deleted Successfully comment",HttpStatus.OK);
    }
	
}
