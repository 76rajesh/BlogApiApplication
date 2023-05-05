package com.example.blogApp.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column(nullable =false, unique =true)
	private String name;
	
	@Column(nullable =false, unique =true)
	private String username;
	
	@Column(nullable =false, unique =true)
	private String email;
	
	@Column(nullable =false, unique =true)
	private String password;
	
	
	@ManyToMany(fetch =FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinTable(name ="user_roles",
	    joinColumns= @JoinColumn(name="user_id", referencedColumnName ="id"),
	    inverseJoinColumns =@JoinColumn(name="role_id", referencedColumnName ="id"))
	private Set<Role> role;
}
