package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ConnectionService connectionService;

	public Collection<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public String deleteByUser(User user) {

		boolean userDeleted = false;
		UserDto userDto = mapUserDtoService.convertUserToUserDto(user);

		List<Transaction> transactionsAsSenderFound = transactionService.getTransactionBySenderUserDto(userDto);

		// Verify if user has made a transaction
		if (transactionsAsSenderFound.size() == 0) {
			List<Transaction> transactionsAsRecipientFound = transactionService
					.getTransactionByRecipientUserDto(userDto);

			// If no, verify if user has received a transaction
			if (transactionsAsRecipientFound.size() == 0) {

				// If no too, delete user
				connectionService.deleteConnectionsByUserConnection(user);
				userRepository.delete(user);
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
