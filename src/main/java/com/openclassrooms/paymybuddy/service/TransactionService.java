package com.openclassrooms.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.model.TransactionDto;
import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.service.MapUserDtoService;
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

	@Autowired
	private MapUserDtoService mapUserDtoService;

	public Collection<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	public List<Transaction> getTransactionsByFee(Fee fee) {
		return transactionRepository.findAllByFee(fee);
	}

	public Optional<Transaction> getTranscationById(int id) {
		return transactionRepository.findById(id);
	}

	public List<Transaction> getTransactionsBySenderUserDto(UserDto senderUserDto) {
		return transactionRepository.findBySenderUserDto(senderUserDto);
	}

	public List<Transaction> getTransactionsByRecipientUserDto(UserDto recipientUserDto) {
		return transactionRepository.findByRecipientUserDto(recipientUserDto);
	}

	@Transactional
	public String saveTransaction(TransactionDto transactionDto, int userId) {

		User userSender = userService.getUserById(userId).get();

		// Transaction amount can't be <= 0
		if (transactionDto.getAmount() <= 0) {
			return "Amount";

			// Sender user balance can't be < of transaction amount
		} else if (userSender.getBalance() < transactionDto.getAmount()) {
			return "Balance";

			// If all is ok, proceed : save the transaction and calculate new balances for
			// both sender and recipient users
		} else {
			Transaction transactionToSave = new Transaction();

			UserDto senderUserDto = mapUserDtoService.getUserDtoById(userSender.getUserId()).get();
			UserDto recipientUserDto = mapUserDtoService.getUserDtoByEmail(transactionDto.getRecipientUserEmail())
					.get();

			transactionToSave.setSenderUserDto(senderUserDto);
			transactionToSave.setRecipientUserDto(recipientUserDto);

			transactionToSave.setDate(LocalDateTime.now());
			transactionToSave.setAmount(transactionDto.getAmount());
			transactionToSave.setDescription(transactionDto.getDescription());
			transactionToSave.setFee(feeService.getFeeForTransactionDate(transactionToSave.getDate().toLocalDate()));
			transactionRepository.save(transactionToSave);

			// Modify balances of sender and recipient users
			userSender.setBalance(userSender.getBalance() - transactionToSave.getAmount());
			userService.saveUser(userSender);

			User recipientUser = userService.getUserById(recipientUserDto.getUserId()).get();
			recipientUser.setBalance(recipientUser.getBalance() + transactionToSave.getAmount());
			userService.saveUser(recipientUser);

			return "Transfer saved";
		}

	}

	public List<Transaction> getTransactionsForASenderUserDtoAndBilled(UserDto senderUserDto, boolean billed) {
		return transactionRepository.findAllBySenderUserDtoAndBilled(senderUserDto, billed);
	}

}
