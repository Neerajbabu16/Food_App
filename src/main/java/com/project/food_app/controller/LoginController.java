package com.project.food_app.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.food_app.model.User;
import com.project.food_app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/user_login")          
	    public String loginPage() {
		System.out.println("login controller");
	        return "user_login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {
		System.out.println("login controller post");
		if (userService.authenticate(email, password)) {
			return "redirect:/";
		}
		model.addAttribute("error", "Invalid email or password");
		return "user_login";

	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("user", new User());
		System.out.println("register controller");
		return "register";
		
	}

	@PostMapping("/register")
	public String register(@ModelAttribute User user, Model model) {
		
		Optional<User> existingUser = userService.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			model.addAttribute("error", " * Email already registered ");
			System.out.println("register controller post");
			return "user_login";
		}
		userService.registerUser(user);
		return "redirect:/";

	}

}
