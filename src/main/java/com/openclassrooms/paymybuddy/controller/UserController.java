package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	public Collection<User> getUsers() {
		return userService.getUsers();
	}

	public Optional<User> getUserById(int userId) {
		return userService.getUserById(userId);
	}

	public Optional<User> getUserByEmail(String string) {
		return userService.getUserByEmail(string);
	}

	@PostMapping("/adduser")
	public User saveUser(User userToSave) {

		User userSaved = userService.saveUser(userToSave);

		if (userSaved == null)
			// throw new PayMyBuddyException("The email '" + userToSave.getEmail() + "'
			// already exists.");
			return null;

		return userSaved;
	}

	public String deleteUser(User user) {
		return userService.deleteByUser(user);
	}

}
