package com.example.blogApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.payload.JwtAuthResponse;
import com.example.blogApp.payload.LoginDTO;
import com.example.blogApp.payload.RegisterDTO;
import com.example.blogApp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authservice;

	public AuthController(AuthService authservice) {
		
		this.authservice = authservice;
	}
	// for multiple values value ={"/login","/signup"}
	@PostMapping(value ={"/login","/signup"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO logindto){
		String response = authservice.login(logindto);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(response);
		
		return  ResponseEntity.ok(jwtAuthResponse);
	}
	
	//RST APi for register
	@PostMapping(value={"/register","signup"})
	public  ResponseEntity<String> register(@RequestBody RegisterDTO registerdto){
		String response = authservice.register(registerdto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

}
