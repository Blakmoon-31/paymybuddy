package com.openclassrooms.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.service.FeeService;
import com.openclassrooms.paymybuddy.service.MapUserDtoService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTest {

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private FeeService feeService;

	@Test
	@Order(1)
	public void testSaveTransaction() {
		Transaction newTransaction = new Transaction();

		UserDto userDtoSender = mapUserDtoService.convertUserToUserDto(userController.getUserById(4).get());
		UserDto userDtoRecipient = mapUserDtoService.convertUserToUserDto(userController.getUserById(2).get());

		newTransaction.setSenderUserDto(userDtoSender);
		newTransaction.setRecipientUserDto(userDtoRecipient);
		newTransaction.setAmount(999.88);
		newTransaction.setDate(LocalDateTime.now());
		newTransaction.setDescription("Test de création");
		newTransaction.setFee(feeService.getFeeForTransactionDate(LocalDate.now()));
		newTransaction.setBilled(true);

		newTransaction = transactionController.saveTransaction(newTransaction);

		Optional<User> userSenderAfter = userController.getUserById(4);
		Optional<User> userRecipientAfter = userController.getUserById(2);

		assertTrue(newTransaction.getId() > 0);

		assertThat(userSenderAfter.get().getBalance()).isEqualTo(-999.88);
		assertThat(userRecipientAfter.get().getBalance()).isEqualTo(999.88);

	}

	@Test
	@Order(2)
	public void testGetTransactionsBySenderUserDto() {
		UserDto senderDto = mapUserDtoService.convertUserToUserDto(userController.getUserById(5).get());
		Iterable<Transaction> transactionsList = transactionController.getTransactionsBySenderUserDto(senderDto);

		assertTrue(transactionsList.iterator().hasNext());
	}

	@Test
	@Order(3)
	public void testGetTransactionsByRecipientUserDto() {
		UserDto recipientDto = mapUserDtoService.convertUserToUserDto(userController.getUserById(3).get());
		Iterable<Transaction> transactionsList = transactionController.getTransactionsByRecipientUserDto(recipientDto);

		assertTrue(transactionsList.iterator().hasNext());
	}

	@Test
	@Order(4)
	public void testGetTransactionsNotBilledForASenderUserDto() {
		UserDto userDtoSender = mapUserDtoService.convertUserToUserDto(userController.getUserById(5).get());
		boolean billed = false;
		List<Transaction> transactionsNotBilled = transactionController
				.getTransactionsNotBilledForASenderUserDto(userDtoSender, billed);

		assertThat(transactionsNotBilled.size()).isEqualTo(3);
	}

	@AfterAll
	public void resetTransactionData() {

		User userSender = userController.getUserById(4).get();
		userSender.setBalance(0.00);
		userController.saveUser(userSender);

		User userRecipient = userController.getUserById(2).get();
		userRecipient.setBalance(0.00);
		userController.saveUser(userRecipient);

		UserDto userDtoSender = mapUserDtoService.convertUserToUserDto(userSender);
		Iterable<Transaction> transactionsToDelete = transactionController
				.getTransactionsBySenderUserDto(userDtoSender);

		transactionRepository.deleteAll(transactionsToDelete);

	}

}
