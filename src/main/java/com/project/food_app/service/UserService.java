package com.project.food_app.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.food_app.model.User;
import com.project.food_app.repo.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	 public boolean authenticate(String email, String rawPassword ,HttpSession session) {
	        Optional<User> user = userRepository.findByEmail(email);
	        session.setAttribute("loggedInUser", user.get());
	        return user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword_hash());
	 }

}
