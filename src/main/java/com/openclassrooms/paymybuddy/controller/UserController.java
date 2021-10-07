package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

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

		boolean userDeleted = false;

		List<Transaction> transactionsAsSenderFound = transactionService.getTransactionBySenderUser(user);

		// Verify if user has made a transaction
		if (transactionsAsSenderFound.size() == 0) {
			List<Transaction> transactionsAsRecipientFound = transactionService.getTransactionByRecipientUser(user);

			// If no, verify if user has received a transaction
			if (transactionsAsRecipientFound.size() == 0) {

				// If no too, delete user
				userService.deleteByUser(user);
				userDeleted = true;
			}
		}

		if (userDeleted) {
			return "User deleted";
		} else {
			return "User not deleted : there are transactions for this user";
		}
	}

}
