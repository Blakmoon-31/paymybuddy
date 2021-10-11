package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping("/users")
	public Collection<User> getUsers() {
		return userService.getUsers();
	}

//	@GetMapping("/user/{userId}")
	public Optional<User> getUserById(int userId) {
		return userService.getUserById(userId);
	}

	public Optional<User> getUserByEmail(String string) {
		return userService.getUserByEmail(string);
	}

	public User saveUser(User userToSave) {
		return userService.saveUser(userToSave);
	}

	public String deleteUser(User user) {
		return userService.deleteByUser(user);
	}

}
