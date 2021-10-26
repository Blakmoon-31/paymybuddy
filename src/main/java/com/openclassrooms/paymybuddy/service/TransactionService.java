package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.exceptions.PayMyBuddyException;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private FeeService feeService;

	public Collection<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	public List<Transaction> getTransactionsByFee(Fee fee) {
		return transactionRepository.findAllByFee(fee);
	}

	public Optional<Transaction> getTranscationById(int id) {
		return transactionRepository.findById(id);
	}

	public List<Transaction> getTransactionBySenderUserDto(UserDto senderUserDto) {
		return transactionRepository.findBySenderUserDto(senderUserDto);
	}

	public List<Transaction> getTransactionByRecipientUserDto(UserDto recipientUserDto) {
		return transactionRepository.findByRecipientUserDto(recipientUserDto);
	}

	@Transactional
	public Transaction saveTransaction(Transaction transaction) {
		User userSender = userService.getUserById(transaction.getSenderUserDto().getUserId()).get();

		// Transaction amount can't be < 0
		if (transaction.getAmount() <= 0) {
			throw new PayMyBuddyException("The amount must by positive.");

			// User balance can't be < of transaction amount
		} else if (userSender.getBalance() < transaction.getAmount()) {
			throw new PayMyBuddyException("The user's balance is insufficient.");

			// If all is, proceed : calculate new balances for both sender and recipient and
			// assign the fee for the transaction date
		} else {

			transaction.setFee(feeService.getFeeForTransactionDate(transaction.getDate().toLocalDate()));

			UserDto senderUserDto = transaction.getSenderUserDto();
			User senderUser = userService.getUserById(senderUserDto.getUserId()).get();
			senderUser.setBalance(senderUser.getBalance() - transaction.getAmount());

			userService.saveUser(senderUser);

			UserDto recipientUserDto = transaction.getRecipientUserDto();
			User recipientUser = userService.getUserById(recipientUserDto.getUserId()).get();
			recipientUser.setBalance(recipientUser.getBalance() + transaction.getAmount());

			userService.saveUser(recipientUser);

			return transactionRepository.save(transaction);
		}

	}

	public List<Transaction> getTransactionsForASenderUserDtoAndBilled(UserDto senderUserDto, boolean billed) {
		return transactionRepository.findAllBySenderUserDtoAndBilled(senderUserDto, billed);
	}

}
