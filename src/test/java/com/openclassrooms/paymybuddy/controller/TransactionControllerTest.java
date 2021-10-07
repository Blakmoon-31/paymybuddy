package com.openclassrooms.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.service.FeeService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTest {

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private TransactionRepository transactionRpository;

	@Autowired
	private UserController userController;

	@Autowired
	private FeeService feeService;

	@Test
	@Order(1)
	public void testSaveTransaction() {
		Transaction newTransaction = new Transaction();

		Optional<User> userSender = userController.getUserById(4);
		Optional<User> userRecipient = userController.getUserById(2);

		newTransaction.setSenderUser(userSender.get());
		newTransaction.setRecipientUser(userRecipient.get());
		newTransaction.setAmount(999.88);
		newTransaction.setDate(LocalDateTime.now());
		newTransaction.setDescription("Test de création");
		newTransaction.setFee(feeService.getFeeForTransactionDate(LocalDate.now()));

		newTransaction = transactionController.saveTransaction(newTransaction);

		Optional<User> userSenderAfter = userController.getUserById(4);
		Optional<User> userRecipientAfter = userController.getUserById(2);

		assertTrue(newTransaction.getId() > 0);

		assertThat(userSenderAfter.get().getBalance()).isEqualTo(-999.88);
		assertThat(userRecipientAfter.get().getBalance()).isEqualTo(999.88);

	}

	@Test
	@Order(2)
	public void testGetTransactionBySenderUser() {
		Optional<User> sender = userController.getUserById(5);
		Iterable<Transaction> transactionsList = transactionController.getTransactionBySenderUser(sender.get());

		assertTrue(transactionsList.iterator().hasNext());
	}

	@Test
	@Order(3)
	public void testGetTransactionByRecipientUser() {
		Optional<User> recipient = userController.getUserById(3);
		Iterable<Transaction> transactionsList = transactionController.getTransactionByRecipientUser(recipient.get());

		assertTrue(transactionsList.iterator().hasNext());
	}

	@AfterAll
	public void resetData() {

		User userSender = userController.getUserById(4).get();
		userSender.setBalance(0.00);
		userController.saveUser(userSender);

		User userRecipient = userController.getUserById(2).get();
		userRecipient.setBalance(0.00);
		userController.saveUser(userRecipient);

		Iterable<Transaction> transactionsToDelete = transactionController.getTransactionBySenderUser(userSender);

		transactionRpository.deleteAll(transactionsToDelete);

	}

}
