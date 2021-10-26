package com.openclassrooms.paymybuddy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.model.User;

@Controller
public class MainWebController {

	@Autowired
	private UserController userController;

	@GetMapping("/register")
	public String showRegisterForm(User user) {
		return "register";
	}

	@GetMapping({ "/login", "/" })
	public String showLoginform() {
		return "login";
	}

	@PostMapping("/login")
	public String loginValidate(User userLogin, BindingResult result, Model model) {
		Optional<User> userExists = userController.getUserByEmail(userLogin.getEmail());

		if (userExists.isEmpty()) {
			ObjectError error = new ObjectError("global", "User not found");
			result.addError(error);
			return "login";
		} else if (!userExists.get().getPassword().equals(userLogin.getPassword())) {
			ObjectError error = new ObjectError("global", "Wrong email or password");
			result.addError(error);
			return "login";
		}
		return "transfer";
	}

}
