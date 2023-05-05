package com.example.blogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogApp.entity.Category;

public interface CategoryReposity extends JpaRepository<Category,Long>{

}
