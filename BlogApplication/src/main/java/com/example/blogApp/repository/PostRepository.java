package com.example.blogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogApp.entity.Post;


public interface PostRepository extends JpaRepository<Post,Long>{

	

	List<Post> findByCategoryId(Long categoryId);

}
