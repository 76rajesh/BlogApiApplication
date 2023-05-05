package com.example.blogApp.service;

import java.util.List;

import com.example.blogApp.payload.CommentDTO;

public interface CommentService {
	CommentDTO createComment(long postid, CommentDTO commentdtp);
	
	List<CommentDTO> getCommentPostById(long postid);
    
	CommentDTO getCommentById(long postid,long commentid);
	
	CommentDTO updateComment(long postid,long commentid, CommentDTO commentdto);
	
	void deleteComment(long postid,long commentid);
	
	
}
