package com.example.blogApp.service;



import com.example.blogApp.payload.LoginDTO;
import com.example.blogApp.payload.RegisterDTO;

public interface AuthService {
	
	String login(LoginDTO login);
	
   String register(RegisterDTO register);
}
