package com.openclassrooms.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	public List<Transaction> getTransactionsBySenderUserDto(UserDto userDto) {
		return transactionService.getTransactionsBySenderUserDto(userDto);
	}

	public Iterable<Transaction> getTransactionsByRecipientUserDto(UserDto userDto) {
		return transactionService.getTransactionsByRecipientUserDto(userDto);
	}

	public List<Transaction> getTransactionsForASenderUserDtoAndBilled(UserDto senderUserDto, boolean billed) {
		return transactionService.getTransactionsForASenderUserDtoAndBilled(senderUserDto, billed);
	}

	public Transaction saveTransaction(Transaction transaction) {
		return transactionService.saveTransaction(transaction);

		// TODO : gérer versements depuis/vers compte personnel, user/user ?
	}
}
