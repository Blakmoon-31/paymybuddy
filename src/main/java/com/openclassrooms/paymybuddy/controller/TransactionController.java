package com.openclassrooms.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.dto.UserDto;
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

		UserDto senderUserDto = transactionSaved.getSenderUserDto();
		User senderUser = userService.getUserById(senderUserDto.getUserId()).get();
		senderUser.setBalance(senderUser.getBalance() - transactionSaved.getAmount());

		userService.saveUser(senderUser);

		UserDto recipientUserDto = transactionSaved.getRecipientUserDto();
		User recipientUser = userService.getUserById(recipientUserDto.getUserId()).get();
		recipientUser.setBalance(recipientUser.getBalance() + transactionSaved.getAmount());

		userService.saveUser(recipientUser);

		return transactionSaved;

		// TODO : gérer versements depuis/vers compte personnel, user/user ?
	}

	public Iterable<Transaction> getTransactionsBySenderUserDto(UserDto userDto) {
		return transactionService.getTransactionBySenderUserDto(userDto);
	}

	public Iterable<Transaction> getTransactionsByRecipientUserDto(UserDto userDto) {
		return transactionService.getTransactionByRecipientUserDto(userDto);
	}

	public List<Transaction> getTransactionsNotBilledForASenderUserDto(UserDto senderUserDto, boolean billed) {
		return transactionService.getTransactionsNotBilledForASenderUserDto(senderUserDto, billed);
	}
}
