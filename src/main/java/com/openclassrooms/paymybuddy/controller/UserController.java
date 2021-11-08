package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Collection<User> getUsers() {
		return userService.getUsers();
	}

	public Optional<User> getUserById(int userId) {
		return userService.getUserById(userId);
	}

	public Optional<User> getUserByEmail(String string) {
		return userService.getUserByEmail(string);
	}

	public Double getBalanceByUserId(int userId) {
		Double userBalance = userService.getBalanceByUserId(userId);

		return userBalance;
	}

	@PostMapping("/adduser")
	public String saveUser(User userToSave, BindingResult result, Model model) {

		userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
		User userSaved = userService.saveUser(userToSave);

		if (userSaved == null) {
			ObjectError error = new ObjectError("globalError",
					"The email: " + userToSave.getEmail() + " already exists");
			result.addError(error);
			return "register";
		}
		return "redirect:/login";
	}

	@PutMapping("/profil")
	public String updateUserProfil(User userToUpdate, BindingResult result, Model model, HttpSession httpSession) {
		userToUpdate.setUserId((int) httpSession.getAttribute("userId"));
		User existsUser = userService.getUserById(userToUpdate.getUserId()).get();
		userToUpdate.setPassword(existsUser.getPassword());
		userToUpdate.setUserRole(existsUser.getUserRole());
		userService.saveUser(userToUpdate);
		return "redirect:/profil";
	}

	@PutMapping("/profil/updatePwd")
	public String updatePassword(User userToUpdate, BindingResult result, Model model, HttpSession httpSession) {
		User userNew = userService.getUserById((int) httpSession.getAttribute("userId")).get();
		userNew.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
		userService.saveUser(userNew);
		return "redirect:/profil";
	}

	@PutMapping("/balance")
	public String updateBalance(User userToUpdate, BindingResult result, Model model, HttpSession httpSession) {
		User userNew = userService.getUserById((int) httpSession.getAttribute("userId")).get();
		userNew.setBalance(userToUpdate.getBalance());
		userService.saveUser(userNew);
		return "redirect:/profil";
	}

	public String deleteUser(User user) {
		return userService.deleteByUser(user);
	}

}
