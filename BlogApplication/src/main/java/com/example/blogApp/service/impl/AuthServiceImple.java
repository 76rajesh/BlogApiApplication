package com.example.blogApp.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogApp.entity.Role;
import com.example.blogApp.entity.User;
import com.example.blogApp.exception.BlogApiException;
import com.example.blogApp.payload.LoginDTO;
import com.example.blogApp.payload.RegisterDTO;
import com.example.blogApp.repository.RoleRepository;
import com.example.blogApp.repository.UserRepository;
import com.example.blogApp.security.JwtTokenProvider;
import com.example.blogApp.service.AuthService;

@Service
public class AuthServiceImple implements AuthService
{

	private AuthenticationManager authtentication;
	
	private UserRepository userrepo;
	private RoleRepository rolerepo;
	private PasswordEncoder passwordEncoder;
	
	private JwtTokenProvider jwtTokenProvider;
	
	

	
	public AuthServiceImple(AuthenticationManager authtentication, UserRepository userrepo, 
			RoleRepository rolerepo,
			PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider){
		
		
		this.authtentication = authtentication;
		this.userrepo = userrepo;
		this.rolerepo = rolerepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}


	public String login(LoginDTO login) {
		Authentication auth = authtentication.authenticate
				(new UsernamePasswordAuthenticationToken(
				login.getUsernameOrEmail(),login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtTokenProvider.generateToken(auth);
		return token;
	}


	@Override
	public String register(RegisterDTO register) {
		// check username is already exists
		
		if(userrepo.existsByUsername(register.getUsername())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Username is already exists");
		}
        
		if(userrepo.existsByEmail(register.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"email is already exists");
		}
		
		User user = new User();
		user.setName(register.getName());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		
		Set<Role> role = new HashSet<>();
		Role userRole = rolerepo.findByName("ROLE_USER").get();
		role.add(userRole);
		user.setRole(role);
		
		userrepo.save(user);
		return "User Registered Successfully";
		
		
	}

}

