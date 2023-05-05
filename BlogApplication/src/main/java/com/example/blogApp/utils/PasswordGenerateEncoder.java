package com.example.blogApp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerateEncoder {
	public static void main(String ar[]) {
		PasswordEncoder passwordencoder = new BCryptPasswordEncoder();
		System.out.println(passwordencoder.encode("rajesh"));
		System.out.println(passwordencoder.encode("admin"));
	}

}
