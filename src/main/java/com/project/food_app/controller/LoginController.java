package com.project.food_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.food_app.DTO.LoginDTO;
import com.project.food_app.model.User;
import com.project.food_app.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String loginPage() {
		System.out.println("login controller");
		return "Welcome to the food app";
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginDTO login) {
		
		String email=login.getEmail();
		String password_hash=login.getPassword_hash();
		
		System.out.println("login controller post");
		if (userService.authenticate(email, password_hash)) {
			return "login succussfull";
		}
		//model.addAttribute("error", "Invalid email or password");
		return "login not succussfull";

	}
	@GetMapping("/all_users")
	public List<User> getAllUsers( ) {
		System.out.println("getallusers");
		
		return userService.getAllUsers();
		 
	}
	@PostMapping("/register")
	public String register(@RequestBody User user) {

		Optional<User> existingUser = userService.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			
			System.out.println("register controller post");
			return "email already existed";
		}
		userService.registerUser(user);
		return "registration succussfull";

	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
	

}
