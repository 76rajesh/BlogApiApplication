package com.example.blogApp.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="comments"  )
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	
	private String name;
    private String email;
    private String body;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id", nullable = false)
    private Post post;
    
    
    
}
