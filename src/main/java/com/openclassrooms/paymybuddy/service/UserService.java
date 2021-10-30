package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtoservice.MapUserDtoService;
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

	@Transactional
	public User saveUser(User user) {

		// If the user has an id, it's an update, else it's a creation so control of the
		// email (unique constraint)
		if (user.getUserId() >= 1) {
			return userRepository.save(user);
		} else {
			Optional<User> existsEmail = userRepository.findByEmail(user.getEmail());
			if (existsEmail.isEmpty()) {
				return userRepository.save(user);
			} else {
				return null;
			}
		}

	}

	@Transactional
	public String deleteByUser(User user) {

		boolean userDeleted = false;
		UserDto userDto = mapUserDtoService.getUserDtoById(user.getUserId()).get();

		List<Transaction> transactionsAsSenderFound = transactionService.getTransactionsBySenderUserDto(userDto);

		// Verify if user has made a transaction
		if (transactionsAsSenderFound.size() == 0) {
			List<Transaction> transactionsAsRecipientFound = transactionService
					.getTransactionsByRecipientUserDto(userDto);

			// If no, verify if user has received a transaction
			if (transactionsAsRecipientFound.size() == 0) {

				// If no too, delete user
				connectionService.deleteConnectionsByUserConnection(user);
				connectionService.deleteConnectionsByConnectedUser(user);
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

	public Double getBalanceByUserId(int userId) {
		return userRepository.findBalanceByUserId(userId);
	}

}
