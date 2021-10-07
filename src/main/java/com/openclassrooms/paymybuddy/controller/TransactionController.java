package com.openclassrooms.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	public Transaction saveTransaction(Transaction transaction) {

		Transaction transactionSaved = transactionService.saveTransaction(transaction);

		User senderUser = transactionSaved.getSenderUser();
		senderUser.setBalance(senderUser.getBalance() - transactionSaved.getAmount());

		userService.saveUser(senderUser);

		User recipientUser = transactionSaved.getRecipientUser();
		recipientUser.setBalance(recipientUser.getBalance() + transactionSaved.getAmount());

		userService.saveUser(recipientUser);

		return transactionSaved;

		// TODO : gérer versements depuis/vers compte personnel, user/user ?
	}

	public Iterable<Transaction> getTransactionBySenderUser(User user) {
		return transactionService.getTransactionBySenderUser(user);
	}

	public Iterable<Transaction> getTransactionByRecipientUser(User user) {
		return transactionService.getTransactionByRecipientUser(user);
	}
}
