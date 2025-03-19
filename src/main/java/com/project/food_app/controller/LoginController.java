package com.project.food_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.food_app.DTO.LoginDTO;
import com.project.food_app.model.User;
import com.project.food_app.repo.UserRepository;
import com.project.food_app.security.JWTUtil;
import com.project.food_app.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO login) {
		System.out.println("logincontroller starting");
		String email = login.getEmail();
		String password_hash = login.getPassword_hash();
		UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password_hash);

		try {
			 Authentication authentication=  authenticationManager.authenticate(credentials);
			 System.out.println("user credentials="+authentication);
		} catch (BadCredentialsException ex) {
			System.err.println("LoginController-Badcredentials");
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("invalid credentials");
		}

		String token = jwtUtil.CreateToken(email);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Authorization", token);

		return ResponseEntity.status(HttpStatus.OK).body("login succussfull,Welcome to the home page:" + email);
	}

	@GetMapping("/all_users")
	public List<User> getAllUsers() {
		System.out.println("getallusers");

		return userService.getAllUsers();

	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {

		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {

			System.out.println("register controller post");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email already existed");
		}
		userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.OK).body("registration succssufull");

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}

}
