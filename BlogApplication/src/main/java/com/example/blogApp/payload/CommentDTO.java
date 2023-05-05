package com.example.blogApp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	
	
	private long id;
	
	@NotEmpty(message ="Not empty should contain letters")
	private String name;
	
	@NotEmpty
	@Email(message ="Email should not be null or empthy ")
	private String email;
	
	@NotEmpty
	@Size(min =10, message ="body must contain 10 characters")
	private String body;
	
	
	

}
