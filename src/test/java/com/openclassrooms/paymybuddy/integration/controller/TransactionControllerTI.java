package com.openclassrooms.paymybuddy.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.openclassrooms.paymybuddy.controller.TransactionController;
import com.openclassrooms.paymybuddy.controller.UserController;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtoservice.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTI {

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private HttpSession httpSession;

	@AfterAll
	public void resetTransactionData() {

		Model model = null;
		BindingResult bindingResult = null;

		User userSender = userController.getUserById(4).get();
		userSender.setBalance(999.88);
		userController.saveUser(userSender, bindingResult, model);

		User userRecipient = userController.getUserById(2).get();
		userRecipient.setBalance(0.00);
		userController.saveUser(userRecipient, bindingResult, model);

		UserDto userDtoSender = mapUserDtoService.getUserDtoById(userSender.getUserId()).get();
		Iterable<Transaction> transactionsToDelete = transactionController
				.getTransactionsBySenderUserDto(userDtoSender);

		transactionRepository.deleteAll(transactionsToDelete);

	}

	@Test
	public void testSaveTransaction() {
		Model model;
		BindingResult bindingResult = null;
		httpSession.setAttribute("userId", 4);

		TransactionDto newTransactionDto = new TransactionDto();

		newTransactionDto.setSenderUserId(4);
		newTransactionDto.setRecipientUserId(2);
		newTransactionDto.setRecipientUserEmail("clara.dupont@mail.fr");

		newTransactionDto.setAmount(999.88);
		newTransactionDto.setDescription("Test de création");

		transactionController.saveTransaction(newTransactionDto, bindingResult, model, httpSession);

		Optional<User> userSenderAfter = userController.getUserById(4);
		Optional<User> userRecipientAfter = userController.getUserById(2);

		assertThat(userSenderAfter.get().getBalance()).isEqualTo(0);
		assertThat(userRecipientAfter.get().getBalance()).isEqualTo(999.88);

	}

	@Test
	public void testGetTransactionsBySenderUserDto() {
		UserDto senderDto = mapUserDtoService.getUserDtoById(5).get();
		Iterable<Transaction> transactionsList = transactionController.getTransactionsBySenderUserDto(senderDto);

		assertThat(transactionsList.iterator().hasNext());
	}

	@Test
	public void testGetTransactionsByRecipientUserDto() {
		UserDto recipientDto = mapUserDtoService.getUserDtoById(3).get();
		Iterable<Transaction> transactionsList = transactionController.getTransactionsByRecipientUserDto(recipientDto);

		assertTrue(transactionsList.iterator().hasNext());
	}

	@Test
	public void testGetTransactionsNotBilledForASenderUserDto() {
		UserDto userDtoSender = mapUserDtoService.getUserDtoById(5).get();
		boolean billed = false;
		List<Transaction> transactionsNotBilled = transactionController
				.getTransactionsForASenderUserDtoAndBilled(userDtoSender, billed);

		assertThat(transactionsNotBilled.size()).isEqualTo(2);
	}

}
