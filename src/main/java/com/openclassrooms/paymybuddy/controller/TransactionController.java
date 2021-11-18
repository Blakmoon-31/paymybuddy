package com.openclassrooms.paymybuddy.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.model.TransactionDto;
import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.service.MapConnectionDtoService;
import com.openclassrooms.paymybuddy.dto.service.MapTransactionDtoService;
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

	@Autowired
	private MapTransactionDtoService mapTransactionDtoService;

	@Autowired
	private MapConnectionDtoService mapConnectionDtoService;

	public List<Transaction> getTransactionsBySenderUserDto(UserDto userDto) {
		return transactionService.getTransactionsBySenderUserDto(userDto);
	}

	public Iterable<Transaction> getTransactionsByRecipientUserDto(UserDto userDto) {
		return transactionService.getTransactionsByRecipientUserDto(userDto);
	}

	public List<Transaction> getTransactionsForASenderUserDtoAndBilled(UserDto senderUserDto, boolean billed) {
		return transactionService.getTransactionsForASenderUserDtoAndBilled(senderUserDto, billed);
	}

	@PostMapping("/transfer")
	public String saveTransaction(TransactionDto transactionDto, BindingResult result, Model model,
			HttpSession httpSession) {
		int userId = (int) httpSession.getAttribute("userId");

		String response = transactionService.saveTransaction(transactionDto, userId);

		if (response == "Amount") {
			model.addAttribute("errorTransfer", "The amount must be > 0");
		} else if (response == "Balance") {
			model.addAttribute("errorTransfer", "Your balance is insufficient for this amount");
		}

		model.addAttribute("transactionsList", mapTransactionDtoService.getTransactionsDtoBySenderId(userId));
		model.addAttribute("transactionsReceivedList",
				mapTransactionDtoService.getTransactionsDtoByRecipientId(userId));
		model.addAttribute("connectionsList", mapConnectionDtoService.getConnectionsDtoByUserId(userId));

		User user = userService.getUserById(userId).get();
		model.addAttribute("user", user);
		TransactionDto newTransaction = new TransactionDto();
		model.addAttribute("transactionDto", newTransaction);

		return "/transfer";

	}
}
