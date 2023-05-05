package com.example.blogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogApp.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{
 // here no need of repository annotation becuase spting 4 have simplerepository implents jparepsoitory and that have tansactional and repository annotation
   List<Comment> findByPostId(long postid);	
	
 }
