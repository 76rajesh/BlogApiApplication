package com.example.blogApp.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blogApp.entity.User;
import com.example.blogApp.repository.UserRepository;


@Service
public class CustomerUserDetailService implements UserDetailsService {

	
	private UserRepository userrepo;

	
	public CustomerUserDetailService(UserRepository userrepo) {
	
		this.userrepo = userrepo;
	}

	
	 @Override
	    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	          User user = userrepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
	                 .orElseThrow(() ->
	                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

	        Set<GrantedAuthority> authorities = user
	                .getRole()
	                .stream()
	                .map((role) -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toSet());

	        return new org.springframework.security.core.userdetails.User(user.getEmail(),
	                user.getPassword(),
	                authorities);
	    }
    
//	@Override
//	public UserDetails loadUserByUsername(String usernameOrEmail) throws 
//	UsernameNotFoundException {
//		// TODO Auto-generated method stub
//	User user =	userrepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(()->
//		new UsernameNotFoundException ("User not found with username or email :"+ usernameOrEmail));
//		
//	   Set<GrantedAuthority> authorities = user.getRole().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
//	   
//	   return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
//	}

}
